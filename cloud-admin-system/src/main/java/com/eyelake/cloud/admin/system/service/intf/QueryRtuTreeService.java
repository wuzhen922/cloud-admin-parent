package com.eyelake.cloud.admin.system.service.intf;

import com.eyelake.cloud.admin.assist.result.RtuTreeResult;

/**
 * 查询实时数据页面传感器树
 */
public interface QueryRtuTreeService {

    /**
     * 查询实时流量页rtu树
     * @param
     * @return
     */
    public RtuTreeResult queryRtuTree();
}
