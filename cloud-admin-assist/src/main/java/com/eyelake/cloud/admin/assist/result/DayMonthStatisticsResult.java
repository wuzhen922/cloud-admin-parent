package com.eyelake.cloud.admin.assist.result;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorMonthDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.OwnerMonthDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.SystemMonthDmo;
import com.yjh.framework.lang.Result;

/**
 * Created by wff on 2018/1/25.
 */
public class DayMonthStatisticsResult extends Result {

    private static final long serialVersionUID = -6189638103975358765L;

    private OwnerMonthDmo ownerMonthDmo;
    private IntegratorMonthDmo integratorMonthDmo;
    private SystemMonthDmo systemMonthDmo;

    public OwnerMonthDmo getOwnerMonthDmo() {
        return ownerMonthDmo;
    }

    public void setOwnerMonthDmo(OwnerMonthDmo ownerMonthDmo) {
        this.ownerMonthDmo = ownerMonthDmo;
    }

    public IntegratorMonthDmo getIntegratorMonthDmo() {
        return integratorMonthDmo;
    }

    public void setIntegratorMonthDmo(IntegratorMonthDmo integratorMonthDmo) {
        this.integratorMonthDmo = integratorMonthDmo;
    }

    public SystemMonthDmo getSystemMonthDmo() {
        return systemMonthDmo;
    }

    public void setSystemMonthDmo(SystemMonthDmo systemMonthDmo) {
        this.systemMonthDmo = systemMonthDmo;
    }
}
