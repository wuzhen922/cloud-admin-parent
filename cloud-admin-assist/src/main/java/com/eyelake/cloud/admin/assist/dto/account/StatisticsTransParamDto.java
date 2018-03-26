package com.eyelake.cloud.admin.assist.dto.account;

import com.eyelake.framework.lang.Request;
import com.yjh.framework.core.entity.Entity;

import java.util.List;


/**
 * 统计传参数的Dto
 * Created by wff on 2018/1/23.
 */
public class StatisticsTransParamDto extends Entity {

    private static final long serialVersionUID = 6217332314023531748L;

    private List<YearStatisticsDto> statisticsList;

    private List<MonthStatisticsDto> dayStatisticsList;


    private List<String> diffList;

    private Long yearPackTraffic ;

    private Double yearOutPackTraffic ;

    private Double yearUseTraffic;


    public List<YearStatisticsDto> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<YearStatisticsDto> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public List<String> getDiffList() {
        return diffList;
    }

    public void setDiffList(List<String> diffList) {
        this.diffList = diffList;
    }

    public Long getYearPackTraffic() {
        return yearPackTraffic;
    }

    public void setYearPackTraffic(Long yearPackTraffic) {
        this.yearPackTraffic = yearPackTraffic;
    }

    public Double getYearOutPackTraffic() {
        return yearOutPackTraffic;
    }

    public void setYearOutPackTraffic(Double yearOutPackTraffic) {
        this.yearOutPackTraffic = yearOutPackTraffic;
    }

    public List<MonthStatisticsDto> getDayStatisticsList() {
        return dayStatisticsList;
    }

    public void setDayStatisticsList(List<MonthStatisticsDto> dayStatisticsList) {
        this.dayStatisticsList = dayStatisticsList;
    }

    public Double getYearUseTraffic() {
        return yearUseTraffic;
    }

    public void setYearUseTraffic(Double yearUseTraffic) {
        this.yearUseTraffic = yearUseTraffic;
    }
}
