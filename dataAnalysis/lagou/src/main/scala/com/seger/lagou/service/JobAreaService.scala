package com.seger.lagou.service

import com.seger.lagou.dao.JobAreaDAO
import com.seger.lagou.stat.JobAreaStat._
import org.apache.spark.sql.DataFrame

import scala.collection.mutable.ListBuffer

object JobAreaService {
  def jobProvinceDFService(jobProvinceDF:DataFrame): Unit ={
    try{
      jobProvinceDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobProvince]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_province = info.getAs[String]("job_province")
          val company_num = info.getAs[Long]("company_num")
          val job_num = info.getAs[Long]("job_num")
          val company_area_num = info.getAs[Long]("company_area_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val job_title_num = info.getAs[Long]("job_title_num")
          list.append(JobProvince(job_province,company_num,job_num,company_area_num,avg_salary,max_salary,min_salary,job_title_num))
        })
        JobAreaDAO.insertJobProvince(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }

  }

  def jobProvinceCityDFService(jobProvinceCityDF:DataFrame): Unit ={
    try{
      jobProvinceCityDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobProvinceCity]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_province = info.getAs[String]("job_province")
          val job_city = info.getAs[String]("job_city")
          val company_num = info.getAs[Long]("company_num")
          val job_num = info.getAs[Long]("job_num")
          val company_area_num = info.getAs[Long]("company_area_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val job_title_num = info.getAs[Long]("job_title_num")
          list.append(JobProvinceCity(job_province,job_city,company_num,job_num,company_area_num,avg_salary,max_salary,min_salary,job_title_num))
        })
        JobAreaDAO.insertJobProvinceCity(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }

  }

  def jobProvinceCityAreaDFService(jobProvinceCityAreaDF:DataFrame): Unit ={
    try{
      jobProvinceCityAreaDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobProvinceCityArea]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val job_province = info.getAs[String]("job_province")
          val job_city = info.getAs[String]("job_city")
          val job_area = info.getAs[String]("job_area")
          val company_num = info.getAs[Long]("company_num")
          val job_num = info.getAs[Long]("job_num")
          val company_area_num = info.getAs[Long]("company_area_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val job_title_num = info.getAs[Long]("job_title_num")
          list.append(JobProvinceCityArea(job_province,job_city,job_area,company_num,job_num,company_area_num,avg_salary,max_salary,min_salary,job_title_num))
        })
        JobAreaDAO.insertJobProvinceCityArea(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
