package com.seger.lagou.stat

object JobNameStat {
  case class JobName(title:String, hot:Long, avg_salary:Double, max_salary:Double, min_salary:Double, avg_experience:Double)
  case class JobNameProvince(title:String, job_province:String, hot:Long, avg_salary:Double, max_salary:Double, min_salary:Double, avg_experience:Double)
  case class JobNameProvinceCity(title:String, job_province:String, job_city:String, hot:Long, avg_salary:Double, max_salary:Double, min_salary:Double, avg_experience:Double)
}
