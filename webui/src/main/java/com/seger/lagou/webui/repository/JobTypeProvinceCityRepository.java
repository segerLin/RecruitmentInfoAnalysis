package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobTypeProvinceCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobTypeProvinceCityRepository extends JpaRepository<JobTypeProvinceCity,String>{
    List<JobTypeProvinceCity> findByJobProvinceAndAndJobCity(String province, String city);
}
