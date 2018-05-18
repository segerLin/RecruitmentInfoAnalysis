package com.seger.lagou.stat

object JobTypeStat {
  case class JobType(job_type:String, num:Long)
  case class JobTypeProvince(job_type:String, job_province:String, num:Long)
  case class JobTypeProvinceCity(job_type:String, job_province:String, job_city:String, num:Long)
}
