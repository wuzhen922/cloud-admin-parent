package com.eyelake.cloud.admin.assist.dto.admin;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.framework.core.entity.Entity;

import java.util.List;

/**
 * @Author CuiXw
 * @Date 2018/1/17
 */
public class BatchInsertRtuDto extends Entity {

    private static final long serialVersionUID = -2857124135123043430L;
    private List<RtuDmo> insertRtuList;

    public List<RtuDmo> getInsertRtuList() {
        return insertRtuList;
    }

    public void setInsertRtuList(List<RtuDmo> insertRtuList) {
        this.insertRtuList = insertRtuList;
    }
}
