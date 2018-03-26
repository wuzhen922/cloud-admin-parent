package com.eyelake.cloud.admin.data.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuPackDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.yjh.framework.lang.Result;

import java.util.List;

/**
 * 网关与套餐关联表定时任务服务
 */
public interface RtuPackService {

    /**
     * 查询rtu所绑定的套餐
     * @param snNumber
     */
    void rtuPackInfo(String snNumber);

    /**
     * 删除RTU与套餐关联
     * @param con
     * @return
     */

    Result deleteRtuPackInfo(RtuPackDmo con);

    /**
     * 查询正常或禁用状态的网关
     *
     * @return
     */

    public List<QueryRtuDto> queryRtuByStatus();
}
