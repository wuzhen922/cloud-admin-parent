package com.eyelake.cloud.admin.data.dto;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuPackDmo;
import com.yjh.framework.core.entity.Entity;

import java.util.List;

/**
 * 网关与套餐关联ListDto
 */
public class RtuPackDto extends Entity{

    private static final long serialVersionUID = 7395240261724248985L;

    private List<RtuPackDmo> rtuPackDmoList;

    public List<RtuPackDmo> getRtuPackDmoList() {
        return rtuPackDmoList;
    }

    public void setRtuPackDmoList(List<RtuPackDmo> rtuPackDmoList) {
        this.rtuPackDmoList = rtuPackDmoList;
    }
}
