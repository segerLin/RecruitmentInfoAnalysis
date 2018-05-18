package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobProvinceCity;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobProvinceCityService {
    List<JobProvinceCity> findByJobProvince(String province);
}
