package com.seger.lagou.webui.service.impl;

import com.seger.lagou.webui.dataobject.ProvinceCity;
import com.seger.lagou.webui.repository.ProvinceCityRepository;
import com.seger.lagou.webui.service.ProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: seger.lin
 */
@Service
public class ProvinceCityServiceImpl implements ProvinceCityService{
    @Autowired
    ProvinceCityRepository repository;

    @Override
    public List<ProvinceCity> findByProvince(String province) {
        return repository.findByProvince(province);
    }
}
