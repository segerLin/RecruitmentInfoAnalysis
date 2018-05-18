package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobProvinceCityArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobProvinceCityAreaRepository extends JpaRepository<JobProvinceCityArea, String> {
    List<JobProvinceCityArea> findByJobProvinceAndJobCity(String province, String city);
}
