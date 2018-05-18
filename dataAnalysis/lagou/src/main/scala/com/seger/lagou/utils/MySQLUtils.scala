package com.seger.lagou.utils

import java.sql.{Connection, DriverManager, PreparedStatement}

object MySQLUtils {
  def getConnection() = {
    DriverManager.getConnection()

  }

  def release(connection:Connection, pstmt:PreparedStatement): Unit ={
    try{
      if(pstmt != null){
        pstmt.close()
      }
    }catch {
      case e: Exception => e.printStackTrace()
    }finally {
      if(connection != null){
        connection.close()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    println(getConnection())
  }



}