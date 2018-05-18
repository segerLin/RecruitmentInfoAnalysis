package com.seger.lagou.utils

object AddrUtils {
  val getArea = (addr:String) => {
    try{
      addr.split("-")(1).replaceAll(" ","").substring(0,3)
    }catch {
      case e : Exception =>{
        ""
      }
    }
  }

  val getCity = (addr:String) => {
    try{
      addr.split("-")(0).replaceAll(" ","").substring(0,2)
    }catch {
      case e : Exception =>{
        if(addr.length > 2)
          addr.substring(0,2)
        else
          ""
      }
    }
  }

  val getProvince = (addr:String) => {
    addr
  }

}
