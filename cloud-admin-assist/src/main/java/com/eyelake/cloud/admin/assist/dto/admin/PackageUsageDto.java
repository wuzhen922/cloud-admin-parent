package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

/**
 * rtu套餐使用情况 dto
 * @author xudajan
 *
 */
public class PackageUsageDto extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;


    /**套餐使用流量(MB)*/
    private Double packUsedTraffic;

    /**
     * 套餐可用流量(MB)
     */

    private Long packTraffic;

    /**
     * 套餐月剩余流量(MB)
     */

    private Double packRemainingTraffic;

    private String packName;


    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Double getPackUsedTraffic() {
        return packUsedTraffic;
    }

    public void setPackUsedTraffic(Double packUsedTraffic) {
        this.packUsedTraffic = packUsedTraffic;
    }

    public Long getPackTraffic() {
        return packTraffic;
    }

    public void setPackTraffic(Long packTraffic) {
        this.packTraffic = packTraffic;
    }

    public Double getPackRemainingTraffic() {
        return packRemainingTraffic;
    }

    public void setPackRemainingTraffic(Double packRemainingTraffic) {
        this.packRemainingTraffic = packRemainingTraffic;
    }
}
