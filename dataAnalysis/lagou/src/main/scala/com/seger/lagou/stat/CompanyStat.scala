package com.seger.lagou.stat

object CompanyStat {
  case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
  case class JobCompanySize(company_size:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long, company_num:Long)
  case class JobCompanyNop(company_nop:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long, company_num:Long)
  case class JobCompanyName(company_name:String, job_num:Long, avg_salary:Double, min_salary:Double, max_salary:Double, max_salary_job:String, hot_job:String, hot_job_num:Long, job_type_num:Long)
}
