package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobTypeProvinceCity;
import com.seger.lagou.webui.repository.JobTypeProvinceCityRepository;
import com.seger.lagou.webui.service.JobTypeProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */

@Service
public class JobTypeProvinceCityServiceImpl implements JobTypeProvinceCityService {
    @Autowired
    private JobTypeProvinceCityRepository repository;

    @Override
    public List<JobTypeProvinceCity> findByJobProvinceAndJobCity(String province, String city) {
        return repository.findByJobProvinceAndAndJobCity(province,city);
    }
}
