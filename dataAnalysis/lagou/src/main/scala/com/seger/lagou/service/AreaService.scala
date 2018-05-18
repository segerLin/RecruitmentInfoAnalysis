package com.seger.lagou.service

import com.seger.lagou.dao.AreaDAO
import com.seger.lagou.stat.AreaStat.{Province,City}
import org.apache.spark.sql.DataFrame

import scala.collection.mutable.ListBuffer

object AreaService {
  def provinceDFService(provinceDF:DataFrame): Unit ={
    try{
      provinceDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[Province]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val province_name = info.getAs[String]("name")
          val code = info.getAs[String]("code")
          list.append(Province(province_name, code))
        })
        AreaDAO.insertProvince(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def cityDFService(cityDF:DataFrame): Unit ={
    try{
      cityDF.foreachPartition(partitionOfRecords => {
        var list = new ListBuffer[City]
        partitionOfRecords.foreach(info => {
          //          case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long,company_num:Long)
          val city_name = info.getAs[String]("name")
          val code = info.getAs[String]("code")
          val province_code = info.getAs[String]("provinceCode")
          list.append(City(city_name, code, province_code))
        })
        AreaDAO.insertCity(list)
      })
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
