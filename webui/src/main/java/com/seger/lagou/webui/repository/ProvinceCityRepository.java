package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.ProvinceCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface ProvinceCityRepository extends JpaRepository<ProvinceCity, String> {
    List<ProvinceCity> findByProvince(String province);
}
