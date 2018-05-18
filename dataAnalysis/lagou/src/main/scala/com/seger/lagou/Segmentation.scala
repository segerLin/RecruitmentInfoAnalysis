package com.seger.lagou

import java.io.File
import java.io.ObjectInputStream.GetField
import java.util
import java.util.{ArrayList, Collections, Comparator, HashMap, Iterator, Map, TreeMap}

import com.hankcs.hanlp.HanLP
import com.hankcs.hanlp.summary.TextRankKeyword
import com.hankcs.hanlp.tokenizer.{NLPTokenizer, SpeedTokenizer, StandardTokenizer}
import org.apache.spark.sql.functions.udf
import org.codehaus.jackson.map.ObjectMapper

import scala.collection.JavaConversions._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Segmentation {
  def jobCompanyAreaSeg(spark:SparkSession,df:DataFrame,size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{
      mapper.writeValue(new File("/company/data/jobCompanyArea.json"),
        textBankKeyWord(spark,df,"job_advantage","company_area",size))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
  def jobCompanySizeSeg(spark:SparkSession,df:DataFrame,size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{
      mapper.writeValue(new File("/company/data/jobCompanySize.json"),
        textBankKeyWord(spark,df,"job_advantage","company_size",size))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobCompanyNopSeg(spark:SparkSession,df:DataFrame,size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{
      mapper.writeValue(new File("/company/data/jobCompanyNop.json"),
        textBankKeyWord(spark,df,"job_advantage","company_nop",10))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobCompanyNameSeg(spark:SparkSession,df:DataFrame,size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{
      mapper.writeValue(new File("/company/data/jobCompanyName.json"),
        textBankKeyWord(spark,df,"job_advantage","company_name",size))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobNameDescSeg(spark:SparkSession, df:DataFrame, filename:String, size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{
      mapper.writeValue(new File("/Desc/data/" + filename + ".json"),
        textBankKeyWord(spark,df,"job_desc","title",size))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobNameDescSeg(spark:SparkSession,df:DataFrame, jobName:String, filename:String,size:Int):Unit ={
    import spark.implicits._
    val cleanData = (data:String) =>{
      jobName
    }
    val myudf = udf(cleanData)
    val newDF = df.select("job_desc", "title")
      .where("title like '%" + jobName +  "%'").withColumn("title", myudf($"title"))
    jobNameDescSeg(spark,newDF,filename,size)
  }


  def jobNameTagSeg(spark:SparkSession,df:DataFrame,filename:String,size:Int): Unit ={
    val mapper = new ObjectMapper()
    try{

      mapper.writeValue(new File("/Tag/data/" + filename + ".json"),
        textBankKeyWord(spark,df,"tags","title",size))
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }

  def jobNameTagSeg(spark:SparkSession,df:DataFrame, jobName:String, filename:String,size:Int):Unit ={
    import spark.implicits._
    val cleanData = (data:String) =>{
      jobName
    }
    val myudf = udf(cleanData)
    val newDF = df.select("tags", "title")
      .where("title like '%" + jobName +  "%'").withColumn("title", myudf($"title"))
    jobNameDescSeg(spark,newDF,filename,size)
  }




  def textBankKeyWord(spark:SparkSession,df:DataFrame,getField:String,groupField:String,keyWordSize:Int) ={
    var map:HashMap[String,util.List[String]] = new HashMap[String,util.List[String]]
    var mapAccumulator = spark.sparkContext.collectionAccumulator[HashMap[String,util.List[String]]]
    df.foreachPartition(partition =>{
      partition.foreach(info =>{
        var getF = info.getAs[String](getField)
        val groupF = info.getAs[String](groupField)
        if(getF != null && groupF != null){
          if(! map.containsKey(groupF)) {
            map.put(groupF, TextRankKeyword.getKeywordList(getF, keyWordSize))
          }else{
            map.get(groupF).addAll(TextRankKeyword.getKeywordList(getF, keyWordSize))
          }
        }
      })
      mapAccumulator.add(map)
    })
    val it:Iterator[HashMap[String,util.List[String]]] = mapAccumulator.value.iterator()
    var temp:HashMap[String,TreeMap[String,Long]] = new HashMap[String,TreeMap[String,Long]]
    while (it.hasNext){
      val item = it.next()
      for(key <- item.keySet()){
        if(!temp.containsKey(key))
          temp.put(key,new TreeMap[String,Long]())
        for(listItem <- item.get(key)){
          if(!temp.get(key).containsKey(listItem))
            temp.get(key).put(listItem,1)
          else
            temp.get(key).put(listItem,temp.get(key).get(listItem) + 1)
        }
      }
    }

    var res:HashMap[String,util.List[Map.Entry[String, Long]]] = new HashMap[String,util.List[Map.Entry[String, Long]]]
    for(key <- temp.keySet()){
      var list:util.List[Map.Entry[String, Long]] = new ArrayList[Map.Entry[String, Long]](temp.get(key).entrySet())
      Collections.sort(list, new Comparator[Map.Entry[String, Long]] {
        override def compare(o1: Map.Entry[String, Long], o2: Map.Entry[String, Long]): Int = o2.getValue().compareTo(o1.getValue)
      })
      if(! res.containsKey(key))
        res.put(key,new ArrayList[Map.Entry[String, Long]]())
      if(list.length < keyWordSize)
        res.get(key).addAll(list)
      else
        res.get(key).addAll(list.subList(0,keyWordSize))
    }
    res
  }

//  def main(args: Array[String]): Unit = {
////    var text = "职位描述：\n\n1、基于招聘业务场景进行深度数据挖掘，优化提升推荐系统整体效果；\n\n2、构建基于用户群体、职位场景等高性能、强适应、可扩展的数据挖掘模型，并有效支撑推荐/搜索等业务需求；\n\n3、将业内先进算法应用到业务场景，提高团队整体技术攻关能力。\n\n\n\n任职要求：\n\n1、计算机相关专业，机器学习领域有2-4年工作经验；\n\n2、精通主流机器学习算法，对贝叶斯、随机森林、SVM、LR、GBDT、Word2Vec、神经网络等有深入理解,对机器学习和人工智能前沿有持续跟踪和应用转化，对机器学习框架Pytorch/Tensorflow等有实际应用者优先；\n\n3、精通python/Java, Shell 等脚本语言，工程实现能力强，对特征工程、统计学习、神经网络、深度学习等有深入理解和实际应用者优先；\n\n4、数据洞察力，业务理解和沟通力强，乐于分享；\n\n5、推荐系统、NLP、搜索排序算法相关经验者优先。\n\n\n\n加入我们，一起玩转AI！\n"
////    println(HanLP.extractKeyword(text,60))
////
//
//  }
}
