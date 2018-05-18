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
public class JobNameProvince {
    @Id
    private String title;
    private String jobProvince;
    private Integer hot;
    private Double avgSalary;
    private Double maxSalary;
    private Double minSalary;
    private Double avgExperience;
}
