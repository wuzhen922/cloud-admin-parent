package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 流量预览 dto
 * @author xudajan
 *
 */
public class RealTimeTrafficDto extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
/**
 * 节点id*/
private String id;

    /**rtusn码*/
    private String snNumber;

    /**月使用流量*/
    private Double monthTraffic;

    /**
     * 月总套餐流量
     */

    private Long monthPackTraffic;

    /**
     * 月剩余流量
     */

    private Double monthRemainingTraffic;

    private List<PackageUsageDto> packageUsageDtoList;

    private List<DayTrafficDto> dayTrafficDtoList;

    private List<HashMap> packageNumberMapList;

    private String restDayOfMonth;

    public String getRestDayOfMonth() {
        return restDayOfMonth;
    }

    public void setRestDayOfMonth(String restDayOfMonth) {
        this.restDayOfMonth = restDayOfMonth;
    }

    public List<HashMap> getPackageNumberMapList() {
        return packageNumberMapList;
    }

    public void setPackageNumberMapList(List<HashMap> packageNumberMapList) {
        this.packageNumberMapList = packageNumberMapList;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMonthTraffic() {
        return monthTraffic;
    }

    public void setMonthTraffic(Double monthTraffic) {
        this.monthTraffic = monthTraffic;
    }

    public Long getMonthPackTraffic() {
        return monthPackTraffic;
    }

    public void setMonthPackTraffic(Long monthPackTraffic) {
        this.monthPackTraffic = monthPackTraffic;
    }

    public Double getMonthRemainingTraffic() {
        return monthRemainingTraffic;
    }

    public void setMonthRemainingTraffic(Double monthRemainingTraffic) {
        this.monthRemainingTraffic = monthRemainingTraffic;
    }

    public List<PackageUsageDto> getPackageUsageDtoList() {
        return packageUsageDtoList;
    }

    public void setPackageUsageDtoList(List<PackageUsageDto> packageUsageDtoList) {
        this.packageUsageDtoList = packageUsageDtoList;
    }

    public List<DayTrafficDto> getDayTrafficDtoList() {
        return dayTrafficDtoList;
    }

    public void setDayTrafficDtoList(List<DayTrafficDto> dayTrafficDtoList) {
        this.dayTrafficDtoList = dayTrafficDtoList;
    }
}
