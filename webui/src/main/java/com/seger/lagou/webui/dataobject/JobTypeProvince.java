package com.seger.lagou.webui.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * 按照职位的类型+省份划分
 *
 * @author: seger.lin
 */

@Entity
@Data
@DynamicUpdate
public class JobTypeProvince {

    @Id
    private String jobType;
    private String jobProvince;
    private String num;
}
