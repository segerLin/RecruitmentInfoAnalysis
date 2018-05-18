package com.seger.lagou.webui.repository;

import com.seger.lagou.webui.dataobject.JobTypeProvince;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: seger.lin
 */

public interface JobTypeProvinceRepository extends JpaRepository<JobTypeProvince, String> {
    List<JobTypeProvince> findByJobProvince(String province);
}
