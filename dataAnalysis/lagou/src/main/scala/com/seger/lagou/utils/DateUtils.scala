package com.seger.lagou.utils

import java.text.SimpleDateFormat

/**
  * 2018-03-30T10:18:56.055+0000
  * 日期解析的工具类
  */
object DateUtils {

  //输出日期格式
  val TARGET_FORMAT = new SimpleDateFormat("yyyy-MM-dd")

  val parse = (time:String) => {
    try{
      //TARGET_FORMAT.parse(time.substring(time.indexOf("[") + 1, time.indexOf("T"))).getTime
      time.substring(time.indexOf("2"), time.indexOf("T"))
    }catch{
      case e: Exception => {
        ""
      }
    }
  }

}
