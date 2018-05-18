
--   case class JobCompanyArea(company_area:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long, company_num:Long)
--   case class JobToCompanySize(company_name:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long)
--   case class JobToCompanyNop(company_nop:String, job_num:Long, avg_salary:Double, hot_job:String, hot_job_num:Long)
--   case class JobToCompanyName(company_size:String, job_num:Long, avg_salary:Int, avg_experience:Int, hot_job:String, min_salary:Int, max_salary:String, cloud:Map[String,Int])


create table job_company_area (
  company_area VARCHAR(30) not null,
  job_num INT not NULL ,
  avg_salary DOUBLE not NULL ,
  hot_job VARCHAR(40) not NULL ,
  hot_job_num INT not NULL ,
  company_num INT not NULL ,
  PRIMARY KEY (company_area)
);

create table job_company_size (
  company_size VARCHAR(20) not null,
  job_num INT not NULL ,
  avg_salary DOUBLE not NULL ,
  hot_job VARCHAR(40) not NULL ,
  hot_job_num  INT not NULL ,
  company_num INT not NULL ,
  PRIMARY KEY (company_size)
);

create table job_company_nop (
  company_nop VARCHAR(20) not null,
  job_num INT not NULL ,
  avg_salary DOUBLE not NULL ,
  hot_job VARCHAR(40) not NULL ,
  hot_job_num INT not NULL ,
  company_num INT not NULL ,
  PRIMARY KEY (company_nop)
);

create table job_company_name (
  company_name VARCHAR(40) not null,
  job_num INT not NULL ,
  avg_salary DOUBLE not NULL ,
  min_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  max_salary_job VARCHAR(40) NOT NULL ,
  hot_job VARCHAR(40) not NULL ,
  hot_job_num  INT not NULL ,
  job_type_num INT not NULL ,
  PRIMARY KEY (company_name)
);

CREATE TABLE job_province (
  job_province VARCHAR (10) NOT NULL ,
  company_num INT NOT NULL ,
  job_num INT NOT NULL ,
  company_area_num INT NOT NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  job_title_num INT NOT NULL,
  PRIMARY KEY (job_province)
);

CREATE TABLE job_province_city (
  job_province VARCHAR (20) NOT NULL ,
  job_city VARCHAR (20) NOT NULL ,
  company_num INT NOT NULL ,
  job_num INT NOT NULL ,
  company_area_num INT NOT NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  job_title_num INT NOT NULL,
  PRIMARY KEY (job_province, job_city)
);

CREATE TABLE job_province_city_area (
  job_province VARCHAR (20) NOT NULL ,
  job_city VARCHAR (20) NOT NULL ,
  job_area VARCHAR (20) NOT NULL ,
  company_num INT NOT NULL ,
  job_num INT NOT NULL ,
  company_area_num INT NOT NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  job_title_num INT NOT NULL,
  PRIMARY KEY (job_province, job_city, job_area)
);

CREATE TABLE job_type (
  job_type VARCHAR(5) NOT NULL ,
  num INT NOT NULL ,
  PRIMARY KEY (job_type)
);

CREATE TABLE job_type_province (
  job_type VARCHAR(5) NOT NULL ,
  job_province VARCHAR (20) NOT NULL ,
  num INT NOT NULL ,
  PRIMARY KEY (job_type, job_province)
);

CREATE TABLE job_type_province_city (
  job_type VARCHAR(5) NOT NULL ,
  job_province VARCHAR (20) NOT NULL ,
  job_city VARCHAR (20) NOT NULL ,
  num INT NOT NULL ,
  PRIMARY KEY (job_type, job_province, job_city)
);


CREATE TABLE job_name (
  title VARCHAR (40) NOT NULL ,
  hot INT NOT  NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  avg_experience DOUBLE NOT NULL ,
  PRIMARY KEY (title)
);

CREATE TABLE job_name_province (
  title VARCHAR (40) NOT NULL ,
  job_province VARCHAR (20) NOT NULL,
  hot INT NOT  NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  avg_experience DOUBLE NOT NULL ,
  PRIMARY KEY (title,job_province)
);

CREATE TABLE job_name_province_city (
  title VARCHAR (40) NOT NULL ,
  job_province VARCHAR (20) NOT NULL,
  job_city VARCHAR (20) NOT NULL,
  hot INT NOT  NULL ,
  avg_salary DOUBLE NOT NULL ,
  max_salary DOUBLE NOT NULL ,
  min_salary DOUBLE NOT NULL ,
  avg_experience DOUBLE NOT NULL ,
  PRIMARY KEY (title,job_province,job_city)
);

CREATE TABLE job_public_time(
  public_time VARCHAR (20) NOT NULL ,
  job_num INT NOT NULL ,
  PRIMARY KEY (public_time)
);
CREATE TABLE province(
  province_name VARCHAR (30) NOT NULL ,
  code VARCHAR(10) NOT NULL ,
  PRIMARY KEY (code)
);

CREATE TABLE city(
  city_name VARCHAR (30) NOT NULL ,
  code VARCHAR(10) NOT NULL ,
  province_code VARCHAR(10) NOT NULL ,

  PRIMARY KEY (code)
);



