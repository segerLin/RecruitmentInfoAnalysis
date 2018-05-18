# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     main
   Description :
   Author :       seger
   date：          2018/3/29
-------------------------------------------------
   Change Activity:
                   2018/3/29:
-------------------------------------------------
"""
__author__ = 'seger'

from scrapy.cmdline import execute

import sys
import os

sys.path.append(os.path.dirname((os.path.abspath(__file__))))
execute(["scrapy", "crawl", "lagouAll"])