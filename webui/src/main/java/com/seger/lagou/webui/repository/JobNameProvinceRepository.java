package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobNameProvinceRepository extends JpaRepository<JobNameProvince, String >{
    Page<JobNameProvince> findByJobProvinceOrderByHotDesc(String province, Pageable pageable);
}
