package com.eyelake.cloud.admin.assist.result;


import com.eyelake.cloud.admin.assist.dto.account.YearStatisticsDto;
import com.yjh.framework.lang.Result;

import java.util.List;

/**
 * 年统计返回结果
 * Created by wff on 2018/1/23.
 */
public class YearStatisticsResult extends Result {


    private List<YearStatisticsDto> yearStatisticsList;

    private Long yearPackTraffic;

    private Double yearOutPackTraffic;

    private Double yearUseTraffic;


    public List<YearStatisticsDto> getYearStatisticsList() {
        return yearStatisticsList;
    }

    public void setYearStatisticsList(List<YearStatisticsDto> yearStatisticsList) {
        this.yearStatisticsList = yearStatisticsList;
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

    public Double getYearUseTraffic() {
        return yearUseTraffic;
    }

    public void setYearUseTraffic(Double yearUseTraffic) {
        this.yearUseTraffic = yearUseTraffic;
    }
}
