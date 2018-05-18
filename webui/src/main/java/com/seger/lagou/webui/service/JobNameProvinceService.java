package com.seger.lagou.webui.service;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: seger.lin
 */

public interface JobNameProvinceService {
    Page<JobNameProvince> findByJobProvince(String province, Pageable pageable);
}
