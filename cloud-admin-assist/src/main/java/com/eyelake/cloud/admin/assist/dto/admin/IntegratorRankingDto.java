package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

/**
 * 集成商企业排名Dto
 *
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */
public class IntegratorRankingDto extends Entity {

    private static final long serialVersionUID = 3844757793833851736L;

    private Long integratorId;

    private String integratorName;

    /**
     * RTU的月使用流量
     */
    private Double monthTraffic;


    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }

    public String getIntegratorName() {
        return integratorName;
    }

    public void setIntegratorName(String integratorName) {
        this.integratorName = integratorName;
    }

    public Double getMonthTraffic() {
        return monthTraffic;
    }

    public void setMonthTraffic(Double monthTraffic) {
        this.monthTraffic = monthTraffic;
    }
}
