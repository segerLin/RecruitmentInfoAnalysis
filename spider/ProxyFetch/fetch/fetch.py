# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     fetch
   Description :
   Author :       seger
   date：          2018/4/2
-------------------------------------------------
   Change Activity:
                   2018/4/2:
-------------------------------------------------
"""
import threading

__author__ = 'seger'
import requests
import re
from scrapy.selector import Selector
from utils.db import DataBase
db = DataBase()
headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0"
}

class Fetch:

    def get_html(self,url):
        return requests.get(url,headers=headers).text

    def fetch_66ip(self):
        print("fetch 66ip-------")
        url = 'http://www.66ip.cn/nmtq.php?getnum=1000&isp=0&anonymoustype=3&start=&ports=&export=&ipaddress=&area=0&proxytype=1&api=66ip'
        html = requests.get(url)
        p = re.compile('\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{2,5}')
        text = p.findall(html)
        ips = []
        for t in text:
            s = t.split(':')
            one = {}
            one['ip'] = s[0]
            one['port'] = s[1]
            one['protocol'] = 'https'
            if self.judge_ip(one['ip'], one['protocol']):
                db.insert_item(one)
        print("66ip has fetched--------")

    # 西刺代理
    def fetch_xici(self, page=1):
        print("fetch xici--------")
        for i in range(100):
            selector = Selector(text=self.get_html(url="http://www.xicidaili.com/nn/{0}".format(i)))
            all_trs = selector.css('#ip_list tr')
            for tr in all_trs[1:]:
                all_texts = tr.css("td::text").extract()
                match_obj1 = re.match(".*'HTTPS'.*", str(all_texts))
                match_obj2 = re.match(".*'HTTP'.*", str(all_texts))
                proxy_type = ""
                if match_obj1:
                    proxy_type = "https"
                elif match_obj2:
                    proxy_type = "http"
                ip = all_texts[0]
                port = all_texts[1]
                if self.judge_ip(ip, port):
                    db.insert_item(
                        {
                            'ip':ip,
                            'port':port,
                            'protocol':proxy_type
                        }
                    )
        print("xici has fetched------")




    # ip海
    def fetch_iphai(self):
        print('fetching iphai---------')
        url = 'http://www.iphai.com/free/ng'
        selector = Selector(text=self.get_html(url))
        trs = selector.xpath('/html/body/div[2]/div[2]/table/tr').extract()[1:]
        for tr in trs:
            tr = Selector(text=tr)
            one = {}
            one['ip'] = tr.xpath('//tr/td[1]/text()').extract()[0].strip()
            one['port'] = tr.xpath('//tr/td[2]/text()').extract()[0].strip()
            one['protocol'] = 'https' if 'https' in tr.xpath('//tr/td[4]/text()').extract()[
                0].strip().lower() else 'http'  # 对于支持双协议的http,https，保守一点，只选择http
            if self.judge_ip(one['ip'], one['protocol']):
                db.insert_item(one)

        print("iphai has fetched!")


    # 云代理
    def fetch_ip3366(self):
        print('fetching ip3366---------')
        urls = ['http://www.ip3366.net/?page=%d' % i for i in range(1, 8)]
        for url in urls:
            selector = Selector(text=self.get_html(url))
            trs = selector.xpath('//*[@id="list"]/table/tbody/tr').extract()
            for tr in trs:
                tr = Selector(text=tr)
                one = {}
                one['ip'] = tr.xpath('//tr/td[1]/text()').extract()[0]
                one['port'] = tr.xpath('//tr/td[2]/text()').extract()[0]
                one['protocol'] = tr.xpath('//tr/td[4]/text()').extract()[0]
                if self.judge_ip(one['ip'], one['protocol']):
                    db.insert_item(one)
        print('ip3366 has fetched')


    def main(self):
        self.fetch_ip3366()
        self.fetch_iphai()
        self.fetch_xici()
        self.fetch_66ip()
        ip_list = db.get_item()
        pass
        # for ip in ip_list:
        #     t = ProxyConnectionTest(ip['ip'], ip['port'])
        #     t.setDaemon(True)
        #     t.start()
        #     t.join()

    def judge_ip(self, ip, port):
        http_url = 'https://www.baidu.com'
        proxy_url = "http://{0}:{1}".format(ip, port)
        try:
            proxy_dict = {
                'http': proxy_url
            }
            response = requests.get(http_url, proxies=proxy_dict)
        except Exception as e:
            print("Exception: invalid ip and port---{0}:{1}".format(ip, port))
            db.remove_item(ip)
            return False
        else:
            code = response.status_code
            if code >= 200 and code < 300:
                print('valid ip---{0}:{1}'.format(ip, port))
                return True
            else:
                print("invalid ip and port---{0}:{1}".format(ip, port))
                db.remove_item(ip)
                return False

class ProxyConnectionTest(threading.Thread):
    def __init__(self, ip, port):
        threading.Thread.__init__(self)
        self.ip = ip
        self.http_url = 'https://www.baidu.com'
        self.proxy_url = "http://{0}:{1}".format(ip, port)

    def run(self):
        try:
            proxy_dict = {
                'http': self.proxy_url
            }
            response = requests.get(self.http_url, proxies=proxy_dict)
        except Exception as e:
            print("invalid ip and port")
            db.remove_item(self.ip)
            return False
        else:
            code = response.status_code
            if code >= 200 and code < 300:
                print('valid ip')
                return True
            else:
                db.remove_item(self.ip)
                return False
if __name__ == '__main__':
    obj = Fetch()
    obj.main()