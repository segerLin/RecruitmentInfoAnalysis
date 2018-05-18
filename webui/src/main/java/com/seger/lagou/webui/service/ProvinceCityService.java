package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.ProvinceCity;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface ProvinceCityService {
    List<ProvinceCity> findByProvince(String province);
}
