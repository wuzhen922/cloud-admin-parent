package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

/**
 * 网卡预览DTO,根据集成商下的网关状态排名
 * 
 * @author  j_cong
 * @date    2018/01/24
 * @version V1.0
 */
public class RtuWarningStatusDto extends Entity {


    private static final long serialVersionUID = 5118240236135831149L;

    private String name;

    private Long value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
