package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import scala.collection.mutable.ListBuffer
import com.seger.lagou.stat.CompanyStat._
import com.seger.lagou.utils.MySQLUtils
object CompanyDAO {
  def insertJobCompanyArea(list: ListBuffer[JobCompanyArea]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_company_area(company_area, job_num, avg_salary, hot_job, hot_job_num, company_num) value (?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.company_area)
        pstmt.setLong(2, ele.job_num)
        pstmt.setDouble(3, ele.avg_salary)
        pstmt.setString(4, ele.hot_job)
        pstmt.setLong(5, ele.hot_job_num)
        pstmt.setLong(6, ele.company_num)
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
  def insertJobCompanySize(list: ListBuffer[JobCompanySize]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_company_size(company_size, job_num, avg_salary, hot_job, hot_job_num, company_num) value (?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.company_size)
        pstmt.setLong(2, ele.job_num)
        pstmt.setDouble(3, ele.avg_salary)
        pstmt.setString(4, ele.hot_job)
        pstmt.setLong(5, ele.hot_job_num)
        pstmt.setLong(6, ele.company_num)
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
  def insertJobCompanyNop(list: ListBuffer[JobCompanyNop]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_company_nop(company_nop, job_num, avg_salary, hot_job, hot_job_num, company_num) value (?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.company_nop)
        pstmt.setLong(2, ele.job_num)
        pstmt.setDouble(3, ele.avg_salary)
        pstmt.setString(4, ele.hot_job)
        pstmt.setLong(5, ele.hot_job_num)
        pstmt.setLong(6, ele.company_num)
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

  def insertJobCompanyName(list: ListBuffer[JobCompanyName]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null

    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_company_name(company_name, job_num, avg_salary, min_salary,max_salary, max_salary_job, hot_job, hot_job_num, job_type_num) value (?,?,?,?,?,?,?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.company_name)
        pstmt.setLong(2, ele.job_num)
        pstmt.setDouble(3, ele.avg_salary)
        pstmt.setDouble(4, ele.min_salary)
        pstmt.setDouble(5, ele.max_salary)
        pstmt.setString(6, ele.max_salary_job)
        pstmt.setString(7,ele.hot_job)
        pstmt.setLong(8,ele.hot_job_num)
        pstmt.setLong(9,ele.job_type_num)
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
