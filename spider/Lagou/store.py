# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     store.py
   Description :
   Author :       seger
   date：          2018/4/8
-------------------------------------------------
   Change Activity:
                   2018/4/8:
-------------------------------------------------
"""
__author__ = 'seger'

import pymongo
import redis
import json
import sched
import time

def process_item():
    Redis_conn = redis.StrictRedis(host='111.230.109.217', port=6379)
    Mongo_conn = pymongo.MongoClient(host='111.230.109.217',port=27017)
    db_name=Mongo_conn['lagou']
    coll_name=db_name['jobInfo']
    count  = 0
    s = sched.scheduler(time.time, time.sleep)
    while True:
        # s.enter(600, 1, push_key)
        # s.run()
        source, data = Redis_conn.blpop(["lagouAll:items"])
        data = json.loads(data.decode('utf-8'))
        p = coll_name.find_one({"_id": data['_id']})
        if not p:
            count += 1
            coll_name.insert(data)
            print("No-" + str(count) + ":" +data['url'] + ' insert successful')

def push_key():
    Redis_conn = redis.StrictRedis(host='111.230.109.217', port=6379)
    if Redis_conn.llen("lagouAll:start_urls") == 0:
        Redis_conn.lpush("lagouAll:start_urls", "https://www.lagou.com")
        print("push start_url")

if __name__ == '__main__':
    # s = sched.scheduler(time.time, time.sleep)
    # while True:
    #     s.enter(600, 1, push_key)
    #     s.run()
    process_item()
# class LagouPipeline(object):
#     def __init__(self):
#         #建立连接
#         self.client = pymongo.MongoClient(host=settings['MONGO_HOST'], port=settings['MONGO_PORT'])
#         #database
#         self.db = self.client[settings['MONGO_DB']]
#         #collections
#         self.coll = self.db[settings['MONGO_COLL']]
#
#     def process_item(self, item, spider):
#         postItem = dict(item)
#         p = self.coll.find_one({"_id": postItem['_id']})
#         if not p:
#             self.coll.insert(postItem)
#         return item


