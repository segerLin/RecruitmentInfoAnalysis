package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobProvinceCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobProvinceCityRepository extends JpaRepository<JobProvinceCity, String> {
    List<JobProvinceCity> findByJobProvince(String province);
}
