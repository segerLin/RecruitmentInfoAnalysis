package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import com.seger.lagou.stat.JobAreaStat._
import com.seger.lagou.utils.MySQLUtils

import scala.collection.mutable.ListBuffer

object JobAreaDAO {
  def insertJobProvince(list: ListBuffer[JobProvince]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_province(job_province, company_num, job_num, company_area_num, avg_salary, max_salary, min_salary, job_title_num) value (?,?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_province)
        pstmt.setLong(2, ele.company_num)
        pstmt.setLong(3, ele.job_num)
        pstmt.setLong(4, ele.company_area_num)
        pstmt.setDouble(5, ele.avg_salary)
        pstmt.setDouble(6, ele.max_salary)
        pstmt.setDouble(7,ele.min_salary)
        pstmt.setLong(8,ele.job_title_num)
        pstmt.addBatch()
      }
      pstmt.executeBatch()
      connection.commit()
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      MySQLUtils.release(connection,pstmt)
    }
  }
  def insertJobProvinceCity(list: ListBuffer[JobProvinceCity]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_province_city(job_province, job_city ,company_num, job_num, company_area_num, avg_salary, max_salary, min_salary, job_title_num) value (?,?,?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_province)
        pstmt.setString(2, ele.job_city)
        pstmt.setLong(3, ele.company_num)
        pstmt.setLong(4, ele.job_num)
        pstmt.setLong(5, ele.company_area_num)
        pstmt.setDouble(6, ele.avg_salary)
        pstmt.setDouble(7, ele.max_salary)
        pstmt.setDouble(8,ele.min_salary)
        pstmt.setLong(9,ele.job_title_num)
        pstmt.addBatch()
      }
      pstmt.executeBatch()
      connection.commit()
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      MySQLUtils.release(connection,pstmt)
    }
  }
  def insertJobProvinceCityArea(list: ListBuffer[JobProvinceCityArea]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_province_city_area(job_province, job_city, job_area, company_num, job_num, company_area_num, avg_salary, max_salary, min_salary, job_title_num) value (?,?,?,?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_province)
        pstmt.setString(2, ele.job_city)
        pstmt.setString(3, ele.job_area)
        pstmt.setLong(4, ele.company_num)
        pstmt.setLong(5, ele.job_num)
        pstmt.setLong(6, ele.company_area_num)
        pstmt.setDouble(7, ele.avg_salary)
        pstmt.setDouble(8, ele.max_salary)
        pstmt.setDouble(9,ele.min_salary)
        pstmt.setLong(10,ele.job_title_num)
        pstmt.addBatch()
      }
      pstmt.executeBatch()
      connection.commit()
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      MySQLUtils.release(connection,pstmt)
    }
  }

}
