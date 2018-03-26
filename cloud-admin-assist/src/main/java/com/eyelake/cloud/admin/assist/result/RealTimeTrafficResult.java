package com.eyelake.cloud.admin.assist.result;


import com.eyelake.cloud.admin.assist.dto.admin.RealTimeTrafficDto;
import com.eyelake.framework.lang.Result;

/**
 * 实时数据页面流量概览result
 * @author xudajan
 */
public class RealTimeTrafficResult extends Result {
    private RealTimeTrafficDto realTimeTrafficDto;

    public RealTimeTrafficDto getRealTimeTrafficDto() {
        return realTimeTrafficDto;
    }

    public void setRealTimeTrafficDto(RealTimeTrafficDto realTimeTrafficDto) {
        this.realTimeTrafficDto = realTimeTrafficDto;
    }
}
