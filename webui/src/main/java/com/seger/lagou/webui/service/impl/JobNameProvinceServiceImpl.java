package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import com.seger.lagou.webui.repository.JobNameProvinceRepository;
import com.seger.lagou.webui.service.JobNameProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */
@Service
public class JobNameProvinceServiceImpl implements JobNameProvinceService {

    @Autowired
    JobNameProvinceRepository repository;

    @Override
    public Page<JobNameProvince> findByJobProvince(String province,Pageable pageable) {
        return repository.findByJobProvinceOrderByHotDesc(province, pageable);
    }
}
