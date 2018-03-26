package com.eyelake.cloud.admin.assist.result;


import com.eyelake.cloud.admin.assist.dto.admin.TreeNodeDto;
import com.eyelake.framework.lang.Result;

import java.util.List;

/**
 * 实时数据页面查询传感器树结构返回结果
 */
public class RtuTreeResult extends Result {


    private List<TreeNodeDto> list;

    public List<TreeNodeDto> getList() {
        return list;
    }

    public void setList(List<TreeNodeDto> list) {
        this.list = list;
    }
}
