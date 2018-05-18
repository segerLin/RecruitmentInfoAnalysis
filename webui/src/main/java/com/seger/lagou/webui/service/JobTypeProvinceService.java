package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobTypeProvince;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobTypeProvinceService {

    List<JobTypeProvince> findByJobProvince(String province);
}
