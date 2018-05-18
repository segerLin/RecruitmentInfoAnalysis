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
public class ProvinceCity {
    @Id
    private Integer code;
    private String province;
    private String city;
}
