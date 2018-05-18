package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import com.seger.lagou.stat.JobTypeStat.{JobType, JobTypeProvince, JobTypeProvinceCity}
import com.seger.lagou.utils.MySQLUtils

import scala.collection.mutable.ListBuffer

object JobTypeDAO {
  def insertJobType(list: ListBuffer[JobType]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_type(job_type, num) value (?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_type)
        pstmt.setLong(2, ele.num)
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
  def insertJobTypeProvince(list: ListBuffer[JobTypeProvince]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_type_province(job_type, job_province, num) value (?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_type)
        pstmt.setString(2, ele.job_province)
        pstmt.setLong(3,ele.num)
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
  def insertJobTypProvinceCity(list: ListBuffer[JobTypeProvinceCity]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_type_province_city(job_type, job_province, job_city, num) value (?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.job_type)
        pstmt.setString(2, ele.job_province)
        pstmt.setString(3, ele.job_city)
        pstmt.setLong(4, ele.num)
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
