# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     db
   Description : 数据库操作
   Author :       seger
   date：          2018/4/1
-------------------------------------------------
   Change Activity:
                   2018/4/1:
-------------------------------------------------
"""
__author__ = 'seger'
import pymongo

#主机ip
MONGO_HOST = '111.230.109.217'
#端口
MONGO_PORT = 27017
#库名
MONGO_DB = 'lagou'
#Collection
MONGO_COLL = 'ip_pools'

class DataBase(object):
    def __init__(self):
        self.client = pymongo.MongoClient(host=MONGO_HOST, port=MONGO_PORT)
        self.db = self.client[MONGO_DB]
        self.coll = self.db[MONGO_COLL]

    def insert_item(self, item):
        postItem = dict(item)
        p = self.coll.find_one({"ip": postItem['ip']})
        if not p:
            self.coll.insert(postItem)
        return item

    def remove_item(self, ip):
        self.coll.delete_one({"ip": ip})


    def get_item(self):
        self.coll.find({})

