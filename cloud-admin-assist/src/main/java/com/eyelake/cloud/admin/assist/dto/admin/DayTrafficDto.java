package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.Column;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;
import java.util.List;

/**
 * 日流量 dto
 * @author xudajan
 *
 */
public class DayTrafficDto extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
    /**
     * 流量日期，具体到年月日
     */
    private Date dayDate;

    /**
     * 日使用流量（KB）
     */
    private Double dayTraffic;

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public Double getDayTraffic() {
        return dayTraffic;
    }

    public void setDayTraffic(Double dayTraffic) {
        this.dayTraffic = dayTraffic;
    }
}
