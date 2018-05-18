package com.seger.lagou.dao

import java.sql.{Connection, PreparedStatement}

import com.seger.lagou.stat.OtherStat.JobPublicTime
import com.seger.lagou.utils.MySQLUtils

import scala.collection.mutable.ListBuffer

object OtherDAO {
  def insertJobPublicTime(list: ListBuffer[JobPublicTime]): Unit ={
    var connection:Connection = null
    var pstmt:PreparedStatement = null
    try{
      connection = MySQLUtils.getConnection()
      connection.setAutoCommit(false)
      val sql = "insert into job_public_time(public_time, job_num) value (?,?)"
      pstmt = connection.prepareStatement(sql)
      for(ele <- list){
        pstmt.setString(1, ele.public_time)
        pstmt.setLong(2, ele.job_num)
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
