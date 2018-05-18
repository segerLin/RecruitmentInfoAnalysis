package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import com.seger.lagou.webui.dataobject.JobNameProvinceCity;
import com.seger.lagou.webui.repository.JobNameProvinceCityRepository;
import com.seger.lagou.webui.repository.JobNameProvinceRepository;
import com.seger.lagou.webui.service.JobNameProvinceCityService;
import com.seger.lagou.webui.service.JobNameProvinceService;
import com.seger.lagou.webui.service.JobTypeProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */
@Service
public class JobNameProvinceCityServiceImpl implements JobNameProvinceCityService {

    @Autowired
    JobNameProvinceCityRepository repository;

    @Override
    public Page<JobNameProvinceCity> findByJobProvinceAndJobCity(String province, String city, Pageable pageable) {
        return repository.findByJobProvinceAndJobCityOrderByHotDesc(province, city, pageable);
    }
}
