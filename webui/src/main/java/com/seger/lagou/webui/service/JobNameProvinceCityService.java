package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import com.seger.lagou.webui.dataobject.JobNameProvinceCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobNameProvinceCityService {
    Page<JobNameProvinceCity> findByJobProvinceAndJobCity(String province, String city, Pageable pageable);
}
