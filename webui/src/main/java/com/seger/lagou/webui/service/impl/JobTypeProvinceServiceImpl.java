package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobTypeProvince;
import com.seger.lagou.webui.repository.JobTypeProvinceRepository;
import com.seger.lagou.webui.service.JobTypeProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */

@Service
public class JobTypeProvinceServiceImpl implements JobTypeProvinceService{

    @Autowired
    private JobTypeProvinceRepository repository;


    @Override
    public List<JobTypeProvince> findByJobProvince(String province) {
        return repository.findByJobProvince(province);
    }
}
