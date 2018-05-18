package com.seger.lagou

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.{avg, count, countDistinct, max, min, to_date, udf}
import com.seger.lagou.Segmentation._
import com.seger.lagou.service.{CompanyService, JobAreaService, JobNameService, JobTypeService, OtherService}
import com.seger.lagou.utils.{AddrUtils, DateUtils}

object LagouAppOnCluster {


  def main(args: Array[String]): Unit = {
    //获得实例
    val spark = SparkSession.builder().getOrCreate()

    if(args.length != 3){
      println("Usage: LagouAppOnCluster <jobPath> <provincePath> <cityPath>")
      System.exit(1)
    }

    //获取数据集
    val path = args(0)
    val provincePath = args(1)
    val cityPath = args(2)

    //加载数据集信息，获得dataframe
    var infoDF = spark.read.json(path)
    val cityDF = spark.read.json(cityPath)
    val provinceDF = spark.read.json(provincePath)

    //创建临时视图，为了支持spark.sql
    infoDF.createOrReplaceTempView("info")
    cityDF.createOrReplaceTempView("city")
    provinceDF.createOrReplaceTempView("province")
    //获取省份、城市对应表
    val cityToProvinceDF = spark.sql("select province.name province, city.name city from province,city where province.code = city.provinceCode ")
    val cityProvinceArray = cityToProvinceDF.collect().map( r => Map(cityToProvinceDF.columns.zip(r.toSeq):_*))
    val cityProvinceMap = Map(cityProvinceArray.map(m => (m.getOrElse("city", null), m.getOrElse("province",null))):_*)

    //数据清洗
    import spark.implicits._
    val dateUDF = udf(DateUtils.parse)
    infoDF = infoDF.withColumn("public_time", dateUDF($"public_time".cast("String")))
    infoDF = infoDF.withColumn("tags", $"tags".cast("String"))

    val getCityUDF = udf(AddrUtils.getCity)
    infoDF = infoDF.withColumn("job_city", getCityUDF($"job_addr"))

    def getProvinceNameByCityName(cityName: String) = {
      if(cityProvinceMap.get(cityName).nonEmpty){
        cityProvinceMap(cityName).toString
      }else if(cityProvinceMap.get(cityName + "市").nonEmpty){
        cityProvinceMap(cityName + "市").toString
      }else if(cityName.length >= 1 && cityProvinceMap.get(cityName.substring(0,2) + "市").nonEmpty){
        cityProvinceMap(cityName.substring(0,2) + "市").toString
      }else {
        if(cityName.length >= 1)
          cityName.substring(0,2)
        else
          cityName
      }
    }


    val getProvince = (cityName:String) =>{
      if(cityName.length > 1)
        getProvinceNameByCityName(cityName.split("-")(0).replace(" ", ""))
      else
        cityName
    }

    val getProvinceUDF = udf(getProvince)
    infoDF = infoDF.withColumn("job_province", getProvinceUDF($"job_addr"))

    val getAreaUDF = udf(AddrUtils.getArea)
    infoDF = infoDF.withColumn("job_area", getAreaUDF($"job_addr"))

    /**
      * 临时视图
      */
    infoDF.createOrReplaceTempView("infoDF")
    /**
      * 根据公司领域分
      */
    def jobCompanyArea(): Unit ={
      spark.sql("select * from infoDF where salary_min != 999 and salary_max != 999").createOrReplaceTempView("info")
      spark.sql("select company_area, title, count(*)as title_num , avg((salary_min + salary_max)/2) avg_salary " +
        "from info group by company_area, title").createOrReplaceTempView("companyAreaTemp")
      spark.sql("select company_area, count(distinct(company_name)) company_num from info group by company_area").createOrReplaceTempView("companyAreaNumTemp");
      var companyAreaDF = spark.sql("select t1.*, t2.company_num from (select Max(company_area) company_area," +
        " Max(hot_job_num) hot_job_num, Max(hot_job) hot_job, " +
        "Max(job_num) job_num, Max(avg_salary) avg_salary " +
        "from(select t1.*, t2.title hot_job " +
        "from (select sum(title_num) job_num, max(title_num) hot_job_num, avg(avg_salary) avg_salary, company_area " +
        "from companyAreaTemp  group by company_area) as t1, companyAreaTemp as t2 " +
        "where t1.company_area = t2.company_area and t1.hot_job_num = t2.title_num) " +
        "group by company_area) as t1, companyAreaNumTemp as t2 where t1.company_area =  t2.company_area order by t2.company_num desc")
      //写入mysql
      CompanyService.companyAreaDFService(companyAreaDF)
    }


    /**
      * 根据公司阶段分
      */

    def jobCompanySize(): Unit ={
      spark.sql("select * from infoDF where salary_min != 999 and salary_max != 999").createOrReplaceTempView("info")
      spark.sql("select company_size, title, count(*) as title_num , avg((salary_min + salary_max)/2) avg_salary " +
        "from info group by company_size, title").createOrReplaceTempView("companySizeTemp")
      spark.sql("select company_size, count(distinct(company_name)) company_num from info group by company_size").createOrReplaceTempView("companySizeNumTemp");
      var companySizeDF = spark.sql("select t1.*, t2.company_num from (select Max(company_size) company_size," +
        " Max(hot_job_num) hot_job_num, Max(hot_job) hot_job, " +
        "Max(job_num) job_num, Max(avg_salary) avg_salary " +
        "from(select t1.*, t2.title hot_job " +
        "from (select sum(title_num) job_num, max(title_num) hot_job_num, avg(avg_salary) avg_salary, company_size " +
        "from companySizeTemp  group by company_size) as t1, companySizeTemp as t2 " +
        "where t1.company_size = t2.company_size and t1.hot_job_num = t2.title_num) " +
        "group by company_size order by hot_job_num desc)as t1, companySizeNumTemp as t2 where t1.company_size =  t2.company_size order by t2.company_num desc")

      CompanyService.companySizeDFService(companySizeDF)
    }

    /**
      * 按照公司人数分
      */
    def jobCompanyNop(): Unit ={
      spark.sql("select * from infoDF where salary_min != 999 and salary_max != 999").createOrReplaceTempView("info")
      spark.sql("select company_nop, title, count(*) as title_num , avg((salary_min + salary_max)/2) avg_salary " +
        "from info group by company_nop, title").createOrReplaceTempView("companyNopTemp")
      spark.sql("select company_nop, count(distinct(company_name)) company_num from info group by company_nop").createOrReplaceTempView("companyNopNumTemp");
      var companyNopDF = spark.sql("select t1.*, t2.company_num from (select Max(company_nop) company_nop," +
        " Max(hot_job_num) hot_job_num, Max(hot_job) hot_job, " +
        "Max(job_num) job_num, Max(avg_salary) avg_salary " +
        "from(select t1.*, t2.title hot_job " +
        "from (select sum(title_num) job_num, max(title_num) hot_job_num, avg(avg_salary) avg_salary, company_nop " +
        "from companyNopTemp  group by company_nop) as t1, companyNopTemp as t2 " +
        "where t1.company_nop = t2.company_nop and t1.hot_job_num = t2.title_num) " +
        "group by company_nop order by hot_job_num desc)as t1, companyNopNumTemp as t2 where t1.company_nop =  t2.company_nop order by t2.company_num desc")
      CompanyService.companyNopeDFService(companyNopDF)
    }


    /**
      * 按照公司名称分
      */
    def jobCompanyName(): Unit ={
      spark.sql("select * from infoDF where salary_min != 999 and salary_max != 999").createOrReplaceTempView("info")
      spark.sql("select company_name, title, count(*) as title_num , avg((salary_min + salary_max)/2) avg_salary, " +
        "min((salary_min + salary_max) /2) min_salary, max((salary_min + salary_max)/2) max_salary from info group by company_name, title").createOrReplaceTempView("companyNameTemp")
      spark.sql("select company_name, count(*) job_type_num from companyNameTemp group by company_name").createOrReplaceTempView("companyNameNumTemp")
      spark.sql("select t1.*, t2.job_type_num from " +
        "(select Max(company_name) company_name," +
        " Max(hot_job_num) hot_job_num, Max(hot_job) hot_job, " +
        " Max(job_num) job_num, Max(avg_salary) avg_salary, " +
        " Min(min_salary) min_salary, Max(max_salary) max_salary" +
        " from(select t1.*, t2.title hot_job " +
        "from (select sum(title_num) job_num, max(title_num) hot_job_num, avg(avg_salary) avg_salary, min(min_salary) min_salary, max(max_salary) max_salary, company_name " +
        "from companyNameTemp  group by company_name) as t1, companyNameTemp as t2 " +
        "where t1.company_name = t2.company_name and t1.hot_job_num = t2.title_num) " +
        "group by company_name)as t1, companyNameNumTemp as t2 " +
        "where t1.company_name =  t2.company_name").createOrReplaceTempView("companyNameTemp_2")
      var companyNameDF = spark.sql("select t1.*, t2.max_salary_job " +
        "from companyNameTemp_2 as t1, " +
        "(select max(title) max_salary_job,company_name, max_salary from companyNameTemp group by company_name, max_salary) as t2 " +
        "where t1.company_name = t2.company_name and t1.max_salary = t2.max_salary")
      CompanyService.companyNameDFService(companyNameDF)
    }

    //    createCompanyAreaDF()
    //    createCompanyNopDF()
    //    createCompanySizeDF()
    //    createCompanyNameDF()


    /**
      * 按照省份进行划分
      * 格式如下：
      *   -省份
      *   -公司数量
      *   -招聘数量
      *   -公司种类数量
      *   -平均工资
      *   -最高工资
      *   -最低工资
      *   -工种数量
      */
    def jobProvince(): Unit ={
      import spark.implicits._
      var df = infoDF.select("*").where("salary_min != 999 or salary_max != 999")
      val jobProvinceDF = df.groupBy("job_province")
        .agg(countDistinct("company_name").as("company_num"),
          count("job_province").as("job_num"),
          countDistinct("company_area").as("company_area_num"),
          avg(($"salary_min" + $"salary_max")/2).as("avg_salary"),
          max(($"salary_min" + $"salary_max")/2).as("max_salary"),
          min(($"salary_min" + $"salary_max")/2).as("min_salary"),
          countDistinct("title").as("job_title_num"))
      //      jobProvinceDF.orderBy($"company_num".desc).show(10)
      JobAreaService.jobProvinceDFService(jobProvinceDF)
    }


    /**
      * 按照市进行划分
      */
    def jobProvinceCity(): Unit ={
      import spark.implicits._
      var df = infoDF.select("*").where("salary_min != 999 or salary_max != 999")
      val jobProvinceCityDF = df.groupBy("job_province","job_city")
        .agg(countDistinct("company_name").as("company_num"),
          count("job_city").as("job_num"),
          countDistinct("company_area").as("company_area_num"),
          avg(($"salary_min" + $"salary_max")/2).as("avg_salary"),
          max(($"salary_min" + $"salary_max")/2).as("max_salary"),
          min(($"salary_min" + $"salary_max")/2).as("min_salary"),
          countDistinct("title").as("job_title_num"))
      //      jobProvinceDF.orderBy($"job_num".desc).show(10)
      JobAreaService.jobProvinceCityDFService(jobProvinceCityDF)
    }

    /**
      * 按照区域进行划分
      */
    def jobProvinceCityArea(): Unit ={
      import spark.implicits._
      var df = infoDF.select("*").where("salary_min != 999 or salary_max != 999")
      val jobProvinceCityAreaDF = df.groupBy("job_province","job_city","job_area")
        .agg(countDistinct("company_name").as("company_num"),
          count("job_area").as("job_num"),
          countDistinct("company_area").as("company_area_num"),
          avg(($"salary_min" + $"salary_max")/2).as("avg_salary"),
          max(($"salary_min" + $"salary_max")/2).as("max_salary"),
          min(($"salary_min" + $"salary_max")/2).as("min_salary"),
          countDistinct("title").as("job_title_num"))
      //      jobProvinceCityAreaDF.orderBy($"job_num".desc).show(10)
      JobAreaService.jobProvinceCityAreaDFService(jobProvinceCityAreaDF)
    }

    /**
      * 按照工作类型进行划分---全国无分组
      */
    def jobType(): Unit ={
      val jobTypeDF = infoDF.groupBy("type")
        .agg(count("type").as("num"))
      JobTypeService.jobTypeDFService(jobTypeDF)

    }

    /**
      * 按照工作类型进行划分---按省份分组
      */
    def jobTypeProvince(): Unit ={
      val jobTypeProvinceDF = infoDF.groupBy("job_province","type")
        .agg(count("type").as("num"))
      JobTypeService.jobTypeProvinceDFService(jobTypeProvinceDF)
    }


    /**
      * 按照工作类型进行划分---按城市分组
      */
    def jobTypeProvinceCity(): Unit ={
      val jobTypeProvinceCityDF = infoDF.groupBy("job_province","job_city","type")
        .agg(count("type").as("num"))
      JobTypeService.jobTypeProvinceCityDFService(jobTypeProvinceCityDF)
    }


    /**
      * 按照职位划分
      * 格式如下：
      *   -职位名称
      *   -平均工资
      *   -最高工资
      *   -最低工资
      *   -平均要求经验
      *   -热度（招聘人数）
      *   -标签词云
      *   -要求词云
      *   -区域划分
      */
    def jobName(province:Boolean, city:Boolean): Unit ={
      import spark.implicits._

      val cleanData = (experience_min:Int,experience_max:Int) => {
        if(experience_max == 999)
          experience_min
        else
          (experience_max + experience_min) / 2
      }
      val dataUdf = udf(cleanData)
      var df = infoDF.withColumn("experience_avg", dataUdf($"experience_min", $"experience_max"))
      df.select("*").where("salary_min != 999 or salary_max != 999").createOrReplaceTempView("jobNameTemp")
      var group = "upper(title)"
      var select = "upper(title) title "
      if(province) {
        group += ",job_province"
        select += ",job_province"
      }
      if(city){
        group += ",job_city"
        select += ",job_city"
      }
      val jobNameDF = spark.sql("select " + select + ",count(title) hot, avg((salary_min + salary_max)/2) avg_salary," +
        "max((salary_min + salary_max) /2) max_salary, min((salary_min + salary_max)/2) min_salary," +
        "avg(experience_avg) avg_experience from jobNameTemp group by " + group)
      //      jobNameDF.orderBy($"max_salary".desc, $"hot".desc).show(10)
      if(province && city){
        JobNameService.jobNameProvinceCityDFService(jobNameDF)
      }else if(province){
        JobNameService.jobNameProvinceDFService(jobNameDF)
      }else{
        JobNameService.jobNameDFService(jobNameDF)
      }
    }

    /**
      * 按照招聘日期分布，可以查看出招聘曲线
      */
    def jobPublicTime(): Unit ={
      var jobPublicTimeDF = infoDF.groupBy("public_time")
        .agg(count("public_time").as("job_num"))
      jobPublicTimeDF.orderBy($"public_time".desc).show(20)
      OtherService.jobPublicTimeDFService(jobPublicTimeDF)
    }

    /**
      * 某一工作工作经验和薪水的关系及薪酬分布
      */
    def jobExperienceAndSalary(keyword:String): Unit ={
      val jobExperienceAndSalaryDF = infoDF.select("experience_max", "experience_min", "salary_max", "salary_min")
        .where("title like '%" +keyword +  "%'")
        .groupBy("experience_max","experience_min").agg(avg(($"salary_min" + $"salary_max")/2).as("avg_salary"), count("*").as("num"))
      //      jobExperienceAndSalaryDF.show(10)
      jobExperienceAndSalaryDF.write.mode(SaveMode.Overwrite).json("/root/data/jobExperienceAndSalaryDF/"+ keyword)
    }

    /**
      * 某一工作薪酬分布
      */

    def jobExperienceDstri(keyword:String): Unit ={
      val jobExperienceDstriDF = infoDF.select("salary_min", "salary_max")
        .where("title like '%" + keyword +"' or title like '%" +keyword +  "%' or title like '" + keyword + "%'")
        .groupBy("salary_min","salary_max").count()
      //      jobExperienceDstriDF.show()
      jobExperienceDstriDF.write.mode(SaveMode.Overwrite).json("/root/data/jobExperienceDstri/"+ keyword)
    }

    /**
      * 某一工作薪酬地区比较
      */
    def jobSalaryAboutCity(keyword:String): Unit ={
      val jobExperienceDstriDF = infoDF.select("job_province", "salary_min", "salary_max")
        .where("title like '%" + keyword +"' or title like '%" +keyword +  "%' or title like '" + keyword + "%'")
        .groupBy("job_province").agg(avg(($"salary_min" + $"salary_max")/2).as("avg_salary"),count("*").as("job_num"))
      //      jobExperienceDstriDF.orderBy($"job_num".desc).show()
      jobExperienceDstriDF.write.mode(SaveMode.Overwrite).json("/root/data/jobSalaryAboutCity/"+ keyword)
    }


//    jobCompanyArea()
//    jobCompanyName()
//    jobCompanyNop()
//    jobCompanySize()
//    jobName(false, false)
    jobName(true,false)
    jobName(true, true)
    jobProvince()
    jobProvinceCity()
    jobProvinceCityArea()
//    jobPublicTime()
//    jobType()
    jobTypeProvince()
    jobTypeProvinceCity()
    jobCompanyAreaSeg(spark,infoDF,10)
//    jobCompanyNameSeg(spark,infoDF,10)
//    jobCompanyNopSeg(spark,infoDF,10)
    jobCompanySizeSeg(spark,infoDF,10)
    var keywords = ".NET,Hadoop,Spark,数据科学,大数据开发,数据分析,NLP,视频算法,unreal,小程序,项目总监,测试总监,安全专家,项目经理,项目助理,产品经理,游戏制作,视觉设计师,网页设计师,Flash设计师,APP设计师,UI设计师,平面设计师美术设计师（2D/3D）,广告设计师,多媒体设计师,原画师,游戏特效,游戏界面设计师,游戏场景,游戏角色,游戏动作,交互设计师,无线交互设计师,网页交互设计师,硬件交互设计师,数据分析师,用户研究员,游戏数值策划,用户研究总监,用户运营,产品运营,数据运营,内容运营,活动运营,商家运营,网络推广,运营专员,网店运营,新媒体运营,海外运营,运营经理,人力资源,招聘,HRBP,培训经理,行政总监,财务总监,CFO,CEO"
    var keyWords = ".NET,Hadoop,Spark,数据科学,大数据开发,数据分析,NLP,视频算法,unreal,小程序,后端开发,Java,C++,PHP,数据挖掘,搜索算法,精准推荐,C,C#,全栈工程师,Python,Delphi,VB,Perl,Ruby,Node.js,Go,ASP,Shell,区块链,HTML5,Android,iOS,WP,移动开发,前端,JavaScript,U3D,COCOS2D-X,人工智能,深度学习,机器学习,图像处理,图像识别,语音识别,机器视觉,算法工程师,自然语言处理,运维工程师,运维开发工程师,网络工程师,系统工程师,IT支持,IDC,CDN,F5系统管理员,病毒分析,WEB安全,网络安全,系统安全,运维经理,MySQL,SQLServer,Oracle,DB2,MongoDB,ETL,Hive,数据仓库,技术经理,技术总监,架构师,CTO,运维总监,技术合伙人,项目总监,测试总监,安全专家,项目经理,项目助理,产品经理,游戏制作,视觉设计师,网页设计师,Flash设计师,APP设计师,UI设计师,平面设计师美术设计师（2D/3D）,广告设计师,多媒体设计师,原画师,游戏特效,游戏界面设计师,游戏场景,游戏角色,游戏动作,交互设计师,无线交互设计师,网页交互设计师,硬件交互设计师,数据分析师,用户研究员,游戏数值策划,用户研究总监,用户运营,产品运营,数据运营,内容运营,活动运营,商家运营,网络推广,运营专员,网店运营,新媒体运营,海外运营,运营经理,人力资源,招聘,HRBP,培训经理,行政总监,财务总监,CFO,CEO"
    for( keyword <- keyWords.split(",")){
      jobExperienceAndSalary(keyword)
    }

    for( keyword <- keyWords.split(",")){
      jobSalaryAboutCity(keyword)
    }

    for( keyword <- keyWords.split(",")){
      jobExperienceDstri(keyword)
    }
    for( keyword <- keyWords.split(",")){
      jobNameTagSeg(spark, infoDF, keyword, keyword+"_tag", 10)
    }

    for(keyword <- keywords.split(",")){
      jobNameDescSeg(spark, infoDF,keyword, keyword+"_desc", 60)
    }
    spark.stop()
  }
}
