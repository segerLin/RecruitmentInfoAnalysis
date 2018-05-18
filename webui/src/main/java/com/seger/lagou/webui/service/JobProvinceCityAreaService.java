package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobProvinceCityArea;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobProvinceCityAreaService {
    List<JobProvinceCityArea> findByJobProvinceAndJobCity(String province, String city);
}
