# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     md5
   Description :
   Author :       seger
   date：          2018/3/31
-------------------------------------------------
   Change Activity:
                   2018/3/31:
-------------------------------------------------
"""
__author__ = 'seger'

import hashlib

def md5(str):
    return hashlib.md5(str.encode('utf-8')).hexdigest()