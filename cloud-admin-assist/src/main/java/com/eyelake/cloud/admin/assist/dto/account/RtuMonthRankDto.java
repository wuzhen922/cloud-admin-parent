package com.eyelake.cloud.admin.assist.dto.account;

import com.yjh.framework.core.entity.Entity;

/**
 * Created by wff on 2018/1/23.
 */
public class RtuMonthRankDto extends Entity {
    private static final long serialVersionUID = -9133831450313104824L;

    private String rtuName;

    private Double rtuMonthTraffic;

    public String getRtuName() {
        return rtuName;
    }

    public void setRtuName(String rtuName) {
        this.rtuName = rtuName;
    }

    public Double getRtuMonthTraffic() {
        return rtuMonthTraffic;
    }

    public void setRtuMonthTraffic(Double rtuMonthTraffic) {
        this.rtuMonthTraffic = rtuMonthTraffic;
    }
}
