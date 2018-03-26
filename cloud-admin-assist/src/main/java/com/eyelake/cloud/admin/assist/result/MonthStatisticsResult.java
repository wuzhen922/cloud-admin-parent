package com.eyelake.cloud.admin.assist.result;

import com.eyelake.cloud.admin.assist.dto.account.MonthStatisticsDto;
import com.eyelake.cloud.admin.assist.dto.account.RtuMonthRankDto;
import com.yjh.framework.lang.Result;

import java.util.List;

/**
 * 月流量统计返回结果类
 * Created by wff on 2018/1/23.
 */
public class MonthStatisticsResult extends Result{

    private List<MonthStatisticsDto> monthStatisticsList;
    //集成商、业主企业月统计rtu排名
    private List<RtuMonthRankDto> rtuMonthRankList;
    //单个rtu 每天使用流量排名
    private List<MonthStatisticsDto> rtuDayTrafficRankList;


    private Long monthPackTraffic;

    private Double monthOutPackTraffic;

    private Double monthUseTraffic;

    public Double getMonthUseTraffic() {
        return monthUseTraffic;
    }

    public void setMonthUseTraffic(Double monthUseTraffic) {
        this.monthUseTraffic = monthUseTraffic;
    }

    public List<MonthStatisticsDto> getMonthStatisticsList() {
        return monthStatisticsList;
    }

    public void setMonthStatisticsList(List<MonthStatisticsDto> monthStatisticsList) {
        this.monthStatisticsList = monthStatisticsList;
    }

    public List<RtuMonthRankDto> getRtuMonthRankList() {
        return rtuMonthRankList;
    }

    public void setRtuMonthRankList(List<RtuMonthRankDto> rtuMonthRankList) {
        this.rtuMonthRankList = rtuMonthRankList;
    }

    public Long getMonthPackTraffic() {
        return monthPackTraffic;
    }

    public void setMonthPackTraffic(Long monthPackTraffic) {
        this.monthPackTraffic = monthPackTraffic;
    }

    public Double getMonthOutPackTraffic() {
        return monthOutPackTraffic;
    }

    public void setMonthOutPackTraffic(Double monthOutPackTraffic) {
        this.monthOutPackTraffic = monthOutPackTraffic;
    }

    public List<MonthStatisticsDto> getRtuDayTrafficRankList() {
        return rtuDayTrafficRankList;
    }

    public void setRtuDayTrafficRankList(List<MonthStatisticsDto> rtuDayTrafficRankList) {
        this.rtuDayTrafficRankList = rtuDayTrafficRankList;
    }
}
