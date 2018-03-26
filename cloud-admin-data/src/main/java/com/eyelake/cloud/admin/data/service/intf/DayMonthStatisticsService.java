package com.eyelake.cloud.admin.data.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.OwnerDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.result.DayMonthStatisticsResult;
import com.yjh.framework.lang.Result;

import java.text.ParseException;
import java.util.List;

/**
 * 日月统计的定时任务接口
 * 
 * @author  wff
 * @date    2018/01/23
 */
public interface DayMonthStatisticsService {
    /*
    *
    * 业主企业日月流量统计
    */
    public void dayMonthOwnerStatistics(OwnerDmo ownerDmo);

    /*
    *
    * 集成商日月流量统计
    */
    public void dayMonthIntegratorStatistics(IntegratorDmo integratorDmo);

    /*
   *
   * 云平台日月流量统计
   */
    public void dayMonthSystemStatistics();

    /*
   * 查所有正常、禁用的业主企业
   */
    List<OwnerDmo> queryOwnerList();

    /*
   * 查所有正常、禁用的集成商
   */
    List<IntegratorDmo> queryIntegratorList();

}
