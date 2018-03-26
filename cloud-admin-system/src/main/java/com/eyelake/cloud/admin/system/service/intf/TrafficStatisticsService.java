package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDayDmo;
import com.eyelake.cloud.admin.assist.result.MonthStatisticsResult;
import com.eyelake.cloud.admin.assist.result.YearStatisticsResult;

/**
 * 流量统计信息服务接口
 */
public interface TrafficStatisticsService extends CommonDao<RtuDayDmo> {


    /**
     * 年云平台流量统计
     */
    public YearStatisticsResult systemYearStatistics(String accountYear);

    /**
     * 月云平台流量统计
     */
    public MonthStatisticsResult systemMonthStatistics(String accountYearMonth);

    /**
     * 集成商月流量统计
     */
    public YearStatisticsResult integratorYearStatistics(String integratorId, String accountYear);



    /**
     * rtu年流量统计
     */
    public YearStatisticsResult rtuYearStatistics(String snNumber, String accountYear);

    /**
     * 集成商月流量统计
     */
    public MonthStatisticsResult integratorMonthStatistics(String integratorId, String accountYearMonth);


    /**
     * rtu月流量统计
     */
    public MonthStatisticsResult rtuMonthStatistics( String snNumber, String accountYearMonth);

}