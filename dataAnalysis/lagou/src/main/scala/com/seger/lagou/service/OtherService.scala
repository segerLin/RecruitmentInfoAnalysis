package com.seger.lagou.service

import com.seger.lagou.stat.OtherStat.JobPublicTime
import com.seger.lagou.dao.OtherDAO
import org.apache.spark.sql.DataFrame


import scala.collection.mutable.ListBuffer

object OtherService {

  def jobPublicTimeDFService(jobPublicTimeDF:DataFrame): Unit ={
    try{
      jobPublicTimeDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[JobPublicTime]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val public_time = info.getAs[String]("public_time")
          val job_num = info.getAs[Long]("job_num")
          list.append(JobPublicTime(public_time, job_num))
        })
        OtherDAO.insertJobPublicTime(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
