package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.Column;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 网关排名Dto
 * 
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */
public class RtuRankingDto extends Entity {


    private static final long serialVersionUID = -4455817559436126950L;

    private Long id;

    /**
     * RTU 名称
     */
    private String rtuName;

    /**
     * RTU的SN码，唯一编号
     */
    private String snNumber;


    /**
     * RTU的月使用流量
     */
    private Double monthTraffic;

    /**
     * RTU所属集成商
     */
    private Long integratorId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRtuName() {
        return rtuName;
    }

    public void setRtuName(String rtuName) {
        this.rtuName = rtuName;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public Double getMonthTraffic() {
        return monthTraffic;
    }

    public void setMonthTraffic(Double monthTraffic) {
        this.monthTraffic = monthTraffic;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }
}
