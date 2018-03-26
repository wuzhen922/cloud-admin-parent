package com.eyelake.cloud.admin.data.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.OwnerDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;

import java.util.List;

/**
 * @author xudajan
 * @date 2018.1.25
 */
public interface MonthService {
/**各种年流量表的维护更新
 * @param rtuDmo*/
   public void rtuYearTrafficTable(RtuDmo rtuDmo);

   public void ownerYearTrafficTable(OwnerDmo ownerDmo);

   public void integratorYearTrafficTable(IntegratorDmo integratorDmo);

   public void systemYearTrafficTable();

    List<RtuDmo> queryRtuList();

    List<OwnerDmo> queryOwnerList();

    List<IntegratorDmo> queryIntegratorList();
}
