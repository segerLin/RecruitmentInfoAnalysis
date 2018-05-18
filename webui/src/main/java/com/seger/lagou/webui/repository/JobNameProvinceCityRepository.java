package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobNameProvinceCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobNameProvinceCityRepository extends JpaRepository<JobNameProvinceCity, String >{
    Page<JobNameProvinceCity> findByJobProvinceAndJobCityOrderByHotDesc (String province, String city, Pageable pageable);
}
