package com.eyelake.cloud.admin.assist.dto.account;

import com.yjh.framework.core.entity.Entity;


/**
 * rtu， 企业，集成商信息，给前端树控件用
 * Created by wff on 2018/1/23.
 */
public class DataDto extends Entity {

    private static final long serialVersionUID = 6217332314023531748L;

    /**
     * RTU的SN码
     */
    private String snNumber;

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

}
