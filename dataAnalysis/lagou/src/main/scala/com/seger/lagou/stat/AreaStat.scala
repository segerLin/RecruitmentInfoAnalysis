package com.seger.lagou.stat

object AreaStat {
  case class Province(province_name:String, code:String)
  case class City(city_name:String, code:String, province_code:String)

}
