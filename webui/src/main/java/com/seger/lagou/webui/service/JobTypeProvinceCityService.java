package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobTypeProvinceCity;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobTypeProvinceCityService {
    List<JobTypeProvinceCity> findByJobProvinceAndJobCity(String province, String city);
}
