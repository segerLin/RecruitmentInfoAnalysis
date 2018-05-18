package com.seger.lagou.stat

object JobAreaStat {
  case class JobProvince(job_province:String, company_num:Long, job_num:Long, company_area_num:Long, avg_salary:Double,max_salary:Double, min_salary:Double,job_title_num:Long)
  case class JobProvinceCity(job_province:String, job_city:String,company_num:Long, job_num:Long, company_area_num:Long, avg_salary:Double,max_salary:Double, min_salary:Double,job_title_num:Long)
  case class JobProvinceCityArea(job_province:String, job_city:String, job_area:String,company_num:Long, job_num:Long, company_area_num:Long, avg_salary:Double,max_salary:Double, min_salary:Double,job_title_num:Long)
}

