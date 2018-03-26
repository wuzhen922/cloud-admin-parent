package com.eyelake.cloud.admin.system.service.intf;

import com.eyelake.cloud.admin.assist.dto.admin.RealTimeTrafficDto;
import com.eyelake.cloud.admin.assist.result.RealTimeTrafficResult;

/**
 * @author:xudajan
 * @date:2018/1/22
 */
public interface RealTimeTrafficService {

   /*rtu流量预览*/
    public RealTimeTrafficResult rtuRealTimeTraffic(RealTimeTrafficDto realTimeTrafficDto);

    /*集成商流量预览*/
    public RealTimeTrafficResult integratorRealTimeTraffic(RealTimeTrafficDto realTimeTrafficDto);
    /*云平台流量预览*/
    public RealTimeTrafficResult systemRealTimeTraffic();
}
