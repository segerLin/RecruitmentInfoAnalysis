package com.seger.lagou.webui.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author: seger.lin
 */

@Entity
@Data
@DynamicUpdate
public class JobProvinceCityArea {
    private String jobProvince;
    private String jobCity;
    @Id
    private String jobArea;
    private Integer companyNum;
    private Integer jobNum;
    private Integer companyAreaNum;
    private Double avgSalary;
    private Double maxSalary;
    private Double minSalary;
    private Integer jobTitleNum;
}
