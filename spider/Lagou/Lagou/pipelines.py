# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import pymongo
from scrapy.conf import settings

class LagouPipeline(object):
    def __init__(self):
        #建立连接
        self.client = pymongo.MongoClient(host=settings['MONGO_HOST'], port=settings['MONGO_PORT'])
        #database
        self.db = self.client[settings['MONGO_DB']]
        #collections
        self.coll = self.db[settings['MONGO_COLL']]

    def process_item(self, item, spider):
        postItem = dict(item)
        p = self.coll.find_one({"_id": postItem['_id']})
        if not p:
            self.coll.insert(postItem)
        return item


