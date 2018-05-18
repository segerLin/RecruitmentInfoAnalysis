package com.seger.lagou.service

import com.seger.lagou.dao.CompanyDAO
import com.seger.lagou.stat.CompanyStat.{JobCompanyArea, JobCompanyName, JobCompanyNop, JobCompanySize}
import org.apache.spark.sql.DataFrame

import scala.collection.mutable.ListBuffer

object CompanyService {
  def companyAreaDFService(companyAreaDF:DataFrame): Unit ={
    try{
      companyAreaDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobCompanyArea]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val company_area = info.getAs[String]("company_area")
          val job_num = info.getAs[Long]("job_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val hot_job = info.getAs[String]("hot_job")
          val hot_job_num = info.getAs[Long]("hot_job_num")
          val company_num = info.getAs[Long]("company_num")
          list.append(JobCompanyArea(company_area,job_num,avg_salary,hot_job,hot_job_num,company_num))
        })
        CompanyDAO.insertJobCompanyArea(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def companySizeDFService(companySizeDF:DataFrame): Unit ={
    try{
      companySizeDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobCompanySize]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val company_size = info.getAs[String]("company_size")
          val job_num = info.getAs[Long]("job_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val hot_job = info.getAs[String]("hot_job")
          val hot_job_num = info.getAs[Long]("hot_job_num")
          val company_num = info.getAs[Long]("company_num")
          list.append(JobCompanySize(company_size,job_num,avg_salary,hot_job,hot_job_num,company_num))
        })
        CompanyDAO.insertJobCompanySize(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }


  def companyNopeDFService(companyNopDF:DataFrame): Unit ={
    try{
      companyNopDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobCompanyNop]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val company_nop = info.getAs[String]("company_nop")
          val job_num = info.getAs[Long]("job_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val hot_job = info.getAs[String]("hot_job")
          val hot_job_num = info.getAs[Long]("hot_job_num")
          val company_num = info.getAs[Long]("company_num")
          list.append(JobCompanyNop(company_nop,job_num,avg_salary,hot_job,hot_job_num,company_num))
        })
        CompanyDAO.insertJobCompanyNop(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }

  }

  def companyNameDFService(companyNameDF:DataFrame): Unit ={
    try{
      companyNameDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobCompanyName]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val company_name = info.getAs[String]("company_name")
          val job_num = info.getAs[Long]("job_num")
          val avg_salary = info.getAs[Double]("avg_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val max_salary_job = info.getAs[String]("max_salary_job")
          val hot_job = info.getAs[String]("hot_job")
          val hot_job_num = info.getAs[Long]("hot_job_num")
          val job_type_num = info.getAs[Long]("job_type_num")
          list.append(JobCompanyName(company_name,job_num,avg_salary,min_salary,max_salary,max_salary_job,hot_job,hot_job_num,job_type_num))
        })
        CompanyDAO.insertJobCompanyName(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
