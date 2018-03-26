package com.eyelake.cloud.admin.assist.dto.admin;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.yjh.framework.core.entity.Entity;

import java.util.List;

/**
 * @Author CuiXw
 * @Date 2018/1/16
 */
public class BatchDeleteRtuDto extends Entity {
    private static final long serialVersionUID = 5918729956386883000L;

    private List<RtuDmo> rtuDmoList;

    private String status;

    public List<RtuDmo> getRtuDmoList() {
        return rtuDmoList;
    }

    public void setRtuDmoList(List<RtuDmo> rtuDmoList) {
        this.rtuDmoList = rtuDmoList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
