# -*- coding: utf-8 -*-
import scrapy
import re
from scrapy.linkextractors import LinkExtractor
from scrapy.spiders import CrawlSpider, Rule
from scrapy_redis.spiders import RedisCrawlSpider
from Lagou.items import LagouJobItemLoader, LagouJobItem
from Lagou.utils.md5 import md5
import datetime

class LagouinfoSpider(RedisCrawlSpider):
    name = 'lagouInfo'
    redis_key = 'lagouInfo:start_urls'
    allowed_domains = ['www.lagou.com']
    # start_urls = ['https://www.lagou.com']

    # if not settings, it will be redirect to login
    custom_settings = {
        "COOKIES_ENABLED": False,
        "DOWNLOAD_DELAY": 1,
        'DEFAULT_REQUEST_HEADERS': {
            'Accept': 'application/json, text/javascript, */*; q=0.01',
            'Accept-Encoding': 'gzip, deflate, br',
            'Accept-Language': 'zh-CN,zh;q=0.8',
            'Connection': 'keep-alive',
            'Cookie': 'JSESSIONID=ABAAABAAAFCAAEGBC99154D1A744BD8AD12BA0DEE80F320; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=0; _ga=GA1.2.1111395267.1516570248; _gid=GA1.2.1409769975.1516570248; user_trace_token=20180122053048-58e2991f-fef2-11e7-b2dc-525400f775ce; PRE_UTM=; LGUID=20180122053048-58e29cd9-fef2-11e7-b2dc-525400f775ce; index_location_city=%E5%85%A8%E5%9B%BD; X_HTTP_TOKEN=7e9c503b9a29e06e6d130f153c562827; _gat=1; LGSID=20180122055709-0762fae6-fef6-11e7-b2e0-525400f775ce; PRE_HOST=github.com; PRE_SITE=https%3A%2F%2Fgithub.com%2Fconghuaicai%2Fscrapy-spider-templetes; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2F4060662.html; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1516569758,1516570249,1516570359,1516571830; _putrc=88264D20130653A0; login=true; unick=%E7%94%B0%E5%B2%A9; gate_login_token=3426bce7c3aa91eec701c73101f84e2c7ca7b33483e39ba5; LGRID=20180122060053-8c9fb52e-fef6-11e7-a59f-5254005c3644; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1516572053; TG-TRACK-CODE=index_navigation; SEARCH_ID=a39c9c98259643d085e917c740303cc7',
            'Host': 'www.lagou.com',
            'Origin': 'https://www.lagou.com',
            'Referer': 'https://www.lagou.com/',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36',
        }
    }

    rules = (
        Rule(LinkExtractor(allow=(r'zhaopin/.*')), follow=True),
        Rule(LinkExtractor(allow=(r'gongsi/j\d+.html')), follow=True),
        Rule(LinkExtractor(allow=r'jobs/\d+.html'), callback='parse_job', follow=True),
    )


    def parse_salary(self, str):
        res = re.match(r'(\d+)[Kk]-(\d+)[Kk]', str)
        if res:
            return res.groups()
        else:
            return (999, 999)

    def parse_experience(self, str):
        s = str
        p = re.match(r'.*?(\d+)-(\d+)', str)
        r1, r2 = 0, 0
        if p:
            r1, r2 = p.groups()
        else:
            p = re.match(r'.*?(\d+)', str)
            if p:
                if str.find("以上"):
                    r1 = p.groups()[0]
                    r2 = 999
                elif str.find('以下'):
                    r1 = 0
                    r2 = p.groups()[0]
        return (r1, r2)

    def parse_time(self, public_time):
        match_time1 = re.match("(\d+):(\d+).*", public_time)
        match_time2 = re.match("(\d+)天前.*", public_time)
        match_time3 = re.match("(\d+)-(\d+)-(\d+)", public_time)
        if match_time1:
            today = datetime.datetime.now()
            hour = int(match_time1.group(1))
            minutues = int(match_time1.group(2))
            time = datetime.datetime(
                today.year, today.month, today.day, hour, minutues)
            return time
        elif match_time2:
            days_ago = int(match_time2.group(1))
            today = datetime.datetime.now() - datetime.timedelta(days=days_ago)
            return today
        elif match_time3:
            year = int(match_time3.group(1))
            month = int(match_time3.group(2))
            day = int(match_time3.group(3))
            today = datetime.datetime(year, month, day)
            return today
        else:
            public_time = datetime.datetime.now()
            return public_time

    def parse_job(self, response):
        item_loader = LagouJobItemLoader(item=LagouJobItem(), response=response)
        item_loader.add_css("title", ".job-name::attr(title)")
        item_loader.add_value("url", response.url)
        item_loader.add_value('_id', md5(response.url))
        (salary_min, salary_max) = self.parse_salary(response.css('.job_request .salary::text').extract()[0])
        item_loader.add_value('salary_min', salary_min)
        item_loader.add_value('salary_max', salary_max)
        (experience_min, experience_max) = self.parse_experience(response.xpath('//*[@class="job_request"]/p/span[3]/text()').extract()[0])
        item_loader.add_value('experience_min', experience_min)
        item_loader.add_value('experience_max', experience_max)
        item_loader.add_xpath('academic', '//*[@class="job_request"]/p/span[4]/text()')
        item_loader.add_xpath('type', '//*[@class="job_request"]/p/span[5]/text()')
        time = self.parse_time(response.css('.publish_time::text').extract()[0])
        item_loader.add_value('public_time', time)
        item_loader.add_css('tags', '.position-label li::text')
        item_loader.add_css('company_name', '#job_company dt a img::attr(alt)')
        item_loader.add_xpath('company_size', '//i[@class="icon-glyph-trend"]/../text()')
        item_loader.add_xpath('company_area', '//i[@class="icon-glyph-fourSquare"]/../text()')
        item_loader.add_xpath('company_nop', '//i[@class="icon-glyph-figure"]/../text()')
        item_loader.add_css('company_url', '#job_company dt a::attr(href)')
        item_loader.add_xpath('job_desc', '//*[@id="job_detail"]/dd[2]/div/p/text()')
        item_loader.add_css('job_advantage', '.job-advantage p::text')
        item_loader.add_xpath('job_city', '//*[@class="job_request"]/p/span[2]/text()')
        item_loader.add_css('job_addr', '.work_addr')
        item_loader.add_value('crawl_time', datetime.datetime.now())

        job_item = item_loader.load_item()
        return job_item
    def parse_item(self, response):
        i = {}
        #i['domain_id'] = response.xpath('//input[@id="sid"]/@value').extract()
        #i['name'] = response.xpath('//div[@id="name"]').extract()
        #i['description'] = response.xpath('//div[@id="description"]').extract()
        return i
