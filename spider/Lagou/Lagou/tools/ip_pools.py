# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     ip_pools
   Description :
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

import random
import requests
class DataBase(object):
    def __init__(self):
        self.client = pymongo.MongoClient(host=MONGO_HOST, port=MONGO_PORT)
        self.db = self.client[MONGO_DB]
        self.coll = self.db[MONGO_COLL]

    def remove_item(self, ip):
        self.coll.delete_one({"ip": ip})


    def get_item(self):
        return list(self.coll.find({}))

    def get_len(self):
        return self.coll.count()

class RandomIP:
    def __init__(self):
        self.db = DataBase()
        self.res = self.db.get_item()

    def judge_ip(self, ip, port):
        http_url = 'https://www.lagou.com'
        proxy_url = "http://{0}:{1}".format(ip, port)
        try:
            proxy_dict = {
                'http': proxy_url
            }
            response = requests.get(http_url, proxies=proxy_dict)
        except Exception as e:
            print("Exception: invalid ip and port---{0}:{1}".format(ip, port))
            self.db.remove_item(ip)
            return False
        else:
            code = response.status_code
            if code >= 200 and code < 300:
                print('valid ip---{0}:{1}'.format(ip, port))
                return True
            else:
                print("invalid ip and port---{0}:{1}".format(ip, port))
                return False

    def get_ip(self):
        num = 0
        while True:
            num = random.randint(0, len(self.res))
            if self.judge_ip(self.res[num]['ip'], self.res[num]['port']):
                break
        return "http://{0}:{1}".format(self.res[num]['ip'], self.res[num]['port'])