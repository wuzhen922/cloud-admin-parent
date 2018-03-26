package com.eyelake.cloud.admin.data.dto;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuPackDmo;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by wff on 2018/1/25.
 */
public class StatisticsDto extends Entity {

    private static final long serialVersionUID = 7395440261724248985L;

    private Long ownerId ;

    private Long integratorId ;

    private Double usedTraffic ;

    private Long packTraffic ;

    private Date statisticsDate;

    public Double getUsedTraffic() {
        return usedTraffic;
    }

    public void setUsedTraffic(Double usedTraffic) {
        this.usedTraffic = usedTraffic;
    }

    public Long getPackTraffic() {
        return packTraffic;
    }

    public void setPackTraffic(Long packTraffic) {
        this.packTraffic = packTraffic;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }
}
