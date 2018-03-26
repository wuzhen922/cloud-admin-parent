package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorDto;
import com.eyelake.cloud.admin.assist.result.DownloadRtuResult;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;

import java.util.List;

/**
 * admin端集成商企业服务接口类
 *
 * @author  j_cong
 * @date    2017-12-12
 * @version V1.0
 */
public interface IntegratorService {

    /**
     * 查询集成商企业列表
     * @param con
     * @param page
     * @return
     */
    public List<QueryIntegratorDto> selectListByPage(QueryIntegratorDto con, Page page);
    /**
     * 根据条件更改集成商状态
     * @param con
     * @return
     */
    public Result changeIntegratorStatus(IntegratorDmo con);


    /**
     * 软删除集成商
     * @param con
     * @return
     */
    public Result deleteIntegrator(IntegratorDmo con);


    /**
     * 查询集成商信息用于下拉框显示
     * @param con
     * @return
     */
    public List<IntegratorDmo> selectList(IntegratorDmo con);

    /**
     * 该集成商下网关信息
     * @param con
     * @return
     */
    public DownloadRtuResult downloadRtuInfo(IntegratorDmo con);
}








