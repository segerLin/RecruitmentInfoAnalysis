package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobProvinceCityArea;
import com.seger.lagou.webui.repository.JobProvinceCityAreaRepository;
import com.seger.lagou.webui.service.JobProvinceCityAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */

@Service
public class JobProvinceCityAreaServiceImpl implements JobProvinceCityAreaService{
    @Autowired
    JobProvinceCityAreaRepository repository;

    @Override
    public List<JobProvinceCityArea> findByJobProvinceAndJobCity(String province, String city) {
        return repository.findByJobProvinceAndJobCity(province,city);
    }
}
