package com.seger.lagou.service

import com.seger.lagou.dao.JobTypeDAO
import com.seger.lagou.stat.JobTypeStat.{JobType,JobTypeProvince,JobTypeProvinceCity}
import org.apache.spark.sql.DataFrame

import scala.collection.mutable.ListBuffer

object JobTypeService {
  def jobTypeDFService(jobTypeDF:DataFrame): Unit ={
    try{
      jobTypeDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobType]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_type = info.getAs[String]("type")
          val num = info.getAs[Long]("num")
          list.append(JobType(job_type, num))
        })
        JobTypeDAO.insertJobType(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobTypeProvinceDFService(jobTypeProvinceDF:DataFrame): Unit ={
    try{
      jobTypeProvinceDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobTypeProvince]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_type = info.getAs[String]("type")
          val job_province = info.getAs[String]("job_province")
          val num = info.getAs[Long]("num")
          list.append(JobTypeProvince(job_type, job_province,num))
        })
        JobTypeDAO.insertJobTypeProvince(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobTypeProvinceCityDFService(jobTypeProvinceCityDF:DataFrame): Unit ={
    try{
      jobTypeProvinceCityDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobTypeProvinceCity]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_type = info.getAs[String]("type")
          val job_province = info.getAs[String]("job_province")
          val job_city = info.getAs[String]("job_city")
          val num = info.getAs[Long]("num")
          list.append(JobTypeProvinceCity(job_type, job_province, job_city, num))
        })
        JobTypeDAO.insertJobTypProvinceCity(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
