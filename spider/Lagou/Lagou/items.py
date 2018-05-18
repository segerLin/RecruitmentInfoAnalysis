# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy
from scrapy.loader.processors import TakeFirst, Identity
from scrapy.loader import ItemLoader
from w3lib.html import remove_tags
from scrapy.loader.processors import TakeFirst, MapCompose, Join

def remove_splash(value):
    return value.replace("/","")

def handle_jobaddr(value):
    addr_list = value.split("\n")
    addr_list = [item.strip() for item in addr_list if item.strip() != "查看地图"]
    return "".join(addr_list)

def format(value):
    return ''.join(value).strip().strip('\n')


class LagouJobItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    """
    职位信息item
    """
    title = scrapy.Field()
    url = scrapy.Field()
    _id = scrapy.Field()
    salary_min = scrapy.Field()
    salary_max = scrapy.Field()
    experience_min = scrapy.Field()
    experience_max = scrapy.Field()
    academic = scrapy.Field(
        input_processor=MapCompose(remove_splash),
    )
    type = scrapy.Field()
    public_time = scrapy.Field()

    tags = scrapy.Field()
    company_name = scrapy.Field()
    company_size = scrapy.Field(
        input_processor=MapCompose(format),
    )
    company_area = scrapy.Field(
        input_processor=MapCompose(format),
    )
    company_nop = scrapy.Field(
        input_processor=MapCompose(format),
    )
    company_url = scrapy.Field()
    job_desc = scrapy.Field(
        input_processor=Join(","),
    )
    job_advantage = scrapy.Field()
    job_city = scrapy.Field(
        input_processor=MapCompose(remove_splash),
    )
    job_addr = scrapy.Field(
        input_processor=MapCompose(remove_tags, handle_jobaddr),
    )
    crawl_time = scrapy.Field()
    crawl_update_time = scrapy.Field()


class LagouJobItemLoader(ItemLoader):
    default_output_processor = TakeFirst()
    tags_out = Identity()



class PositionTitle(scrapy.Item):
    pass