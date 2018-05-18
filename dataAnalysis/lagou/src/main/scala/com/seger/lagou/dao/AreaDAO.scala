package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import com.seger.lagou.stat.AreaStat.{City,Province}
import com.seger.lagou.utils.MySQLUtils

import scala.collection.mutable.ListBuffer

object AreaDAO {
  def insertProvince(list: ListBuffer[Province]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null
    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into province(province_name, code) value (?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.province_name)
        pstmt.setString(2, ele.code)
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
  def insertCity(list: ListBuffer[City]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null
    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into city(city_name, code, province_code) value (?,?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.city_name)
        pstmt.setString(2, ele.code)
        pstmt.setString(3, ele.province_code)
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
