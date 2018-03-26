package com.eyelake.cloud.admin.data.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.result.QueryRtuMonthTrafficAndPackResult;
import com.yjh.framework.lang.Result;

import java.text.ParseException;
import java.util.List;

/**
 * 月定时任务接口
 * 
 * @author  j_cong
 * @date    2018/01/23
 * @version V1.0
 */
public interface DayService {

    /**
     * 根据网关今天与昨天的月流量差，插入网关日流表，
     * 网关月流量记录已存在情况下进入此方法
     *
     * @param rtuDto  查询条件
     * @return 插入结果
     */
    Result insertRtuDayTraffic(QueryRtuDto rtuDto);

    /**
     * 根据网关月流量差，更新网关预警信息状态
     *
     * @param rtuDto  网关信息
     * @return   更新结果
     */
    Result updateRtuWarningStatus(QueryRtuDto rtuDto);

    /**
     * 查询RTU列表
     *
     * @return 网关列表
     */
    List<QueryRtuDto> selectRtuList();


    /**
     * 根据网关snNumber调用QueryCardFlowInfo接口查询月使用流量
     * 同时更新（插入）网关月使用流量，插入网关日使用流量
     *
     * @param rtu  RTU信息
     * @return 接口查询结果
     */
    QueryRtuMonthTrafficAndPackResult queryRtuMonthTrafficAndPack(QueryRtuDto rtu);


    /**
     * 更新（插入）网关月流量信息
     *
     * @param rtuDto  网关snNumber
     * @return 更新结果
     */
    Result updateRtuMonth(QueryRtuDto rtuDto);



}
