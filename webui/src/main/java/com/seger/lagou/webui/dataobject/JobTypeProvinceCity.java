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
public class JobTypeProvinceCity {
    @Id
    private String jobType;
    private String jobProvince;
    private String jobCity;
    private String num;
}
