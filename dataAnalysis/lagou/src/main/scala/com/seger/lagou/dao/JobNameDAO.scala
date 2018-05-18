package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import scala.collection.mutable.ListBuffer
import com.seger.lagou.stat.JobNameStat.{JobName, JobNameProvince, JobNameProvinceCity}
import com.seger.lagou.utils.MySQLUtils


object JobNameDAO {
  def insertJobName(list: ListBuffer[JobName]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_name(title, hot, avg_salary, max_salary, min_salary, avg_experience) value (?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.title)
        pstmt.setLong(2, ele.hot)
        pstmt.setDouble(3, ele.avg_salary)
        pstmt.setDouble(4, ele.max_salary)
        pstmt.setDouble(5, ele.min_salary)
        pstmt.setDouble(6, ele.avg_experience)
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
  def insertJobNameProvince(list: ListBuffer[JobNameProvince]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_name_province(title, job_province, hot, avg_salary, max_salary, min_salary, avg_experience) value (?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.title)
        pstmt.setString(2, ele.job_province)
        pstmt.setLong(3, ele.hot)
        pstmt.setDouble(4, ele.avg_salary)
        pstmt.setDouble(5, ele.max_salary)
        pstmt.setDouble(6, ele.min_salary)
        pstmt.setDouble(7, ele.avg_experience)
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
  def insertJobNameProvinceCity(list: ListBuffer[JobNameProvinceCity]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_name_province_city(title, job_province,job_city, hot, avg_salary, max_salary, min_salary, avg_experience) value (?,?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.title)
        pstmt.setString(2, ele.job_province)
        pstmt.setString(3, ele.job_city)
        pstmt.setLong(4, ele.hot)
        pstmt.setDouble(5, ele.avg_salary)
        pstmt.setDouble(6, ele.max_salary)
        pstmt.setDouble(7, ele.min_salary)
        pstmt.setDouble(8, ele.avg_experience)
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
