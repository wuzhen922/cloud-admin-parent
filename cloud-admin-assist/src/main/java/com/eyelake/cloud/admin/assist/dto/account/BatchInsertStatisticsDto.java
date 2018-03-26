package com.eyelake.cloud.admin.assist.dto.account;

import com.yjh.framework.core.entity.Entity;

import java.util.List;


/**
 * 日、月数据批量插入的Dto
 * Created by wff on 2018/1/23.
 */
public class BatchInsertStatisticsDto extends Entity {

    private static final long serialVersionUID = 6217332314023531748L;

    private List<YearStatisticsDto> monthDataList;

    private List<MonthStatisticsDto> dayDataList;


    public List<YearStatisticsDto> getMonthDataList() {
        return monthDataList;
    }

    public void setMonthDataList(List<YearStatisticsDto> monthDataList) {
        this.monthDataList = monthDataList;
    }

    public List<MonthStatisticsDto> getDayDataList() {
        return dayDataList;
    }

    public void setDayDataList(List<MonthStatisticsDto> dayDataList) {
        this.dayDataList = dayDataList;
    }
}
