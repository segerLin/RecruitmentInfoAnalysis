package com.seger.lagou.service

import org.apache.spark.sql.DataFrame
import scala.collection.mutable.ListBuffer
import com.seger.lagou.stat.JobNameStat.{JobName, JobNameProvince, JobNameProvinceCity}
import com.seger.lagou.dao.JobNameDAO

object JobNameService {
  def jobNameDFService(jobNameDF:DataFrame): Unit ={
    try{
      jobNameDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobName]
        partitionOfRecords.foreach(info => {
          val title = info.getAs[String]("title")
//          val job_province = info.getAs[String]("job_province")
//          val job_city = info.getAs[String]("")
          val hot = info.getAs[Long]("hot")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val avg_experience = info.getAs[Double]("avg_experience")

          list.append(JobName(title, hot, avg_salary, max_salary, min_salary, avg_experience))
        })
        JobNameDAO.insertJobName(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobNameProvinceDFService(jobNameProvinceDF:DataFrame): Unit ={
    try{
      jobNameProvinceDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobNameProvince]
        partitionOfRecords.foreach(info => {
          val title = info.getAs[String]("title")
          val job_province = info.getAs[String]("job_province")
          //  val job_city = info.getAs[String]("")
          val hot = info.getAs[Long]("hot")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val avg_experience = info.getAs[Double]("avg_experience")

          list.append(JobNameProvince(title, job_province, hot, avg_salary, max_salary, min_salary, avg_experience))
        })
        JobNameDAO.insertJobNameProvince(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobNameProvinceCityDFService(jobNameProvinceCityDF:DataFrame): Unit ={
    try{
      jobNameProvinceCityDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobNameProvinceCity]
        partitionOfRecords.foreach(info => {
          val title = info.getAs[String]("title")
          val job_province = info.getAs[String]("job_province")
          val job_city = info.getAs[String]("job_city")
          val hot = info.getAs[Long]("hot")
          val avg_salary = info.getAs[Double]("avg_salary")
          val max_salary = info.getAs[Double]("max_salary")
          val min_salary = info.getAs[Double]("min_salary")
          val avg_experience = info.getAs[Double]("avg_experience")

          list.append(JobNameProvinceCity(title, job_province,job_city, hot, avg_salary, max_salary, min_salary, avg_experience))
        })
        JobNameDAO.insertJobNameProvinceCity(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

}
