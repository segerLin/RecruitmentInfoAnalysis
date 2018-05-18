package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobProvinceCity;
import com.seger.lagou.webui.repository.JobProvinceCityRepository;
import com.seger.lagou.webui.service.JobProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */

@Service
public class JobProvinceServiceImpl implements JobProvinceCityService {

    @Autowired
    JobProvinceCityRepository repository;

    @Override
    public List<JobProvinceCity> findByJobProvince(String province) {
        return repository.findByJobProvince(province);
    }
}
