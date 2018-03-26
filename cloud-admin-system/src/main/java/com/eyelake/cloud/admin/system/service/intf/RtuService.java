package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.result.ImportRtusResult;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * admin端RTU服务接口类
 *
 * @author  j_cong
 * @date    2017-12-12
 * @version V1.0
 */
public interface RtuService {

    /**
     * 查询RTU列表
     * @param con
     * @param page
     * @return
     */
    public List<QueryRtuDto> selectListByPage(QueryRtuDto con, Page page);

    /**
     * 根据条件更改集RTU状态，同时更新该RTU下的传感器
     * @param con
     * @return
     */
    public Result changeRtuStatus(RtuDmo con);


    /**
     * 软删除RTU，同时软删除该RTU下的传感器
     * @param con
     * @return
     */
    public Result deleteRtu(RtuDmo con);

    /**
     * 批量删除RTU，同时软删除RTU下的传感器及参数
     * @param deleteRtuIdList
     * @return
     */
    public Result batchDeleteRtu(List<RtuDmo> deleteRtuIdList);

    /**
     * 导入rtu数据信息
     * @param excelData
     * @return
     */
    public ImportRtusResult importRtu(List<LinkedHashMap<String, Object>> excelData);

    /**
     * 新增Rtu
     * @param con
     * @return
     */
    public Result addRtuInfo(RtuDmo con);

    /**
     * 修改Rtu
     * @param con
     * @return
     */
    public Result updateRtuInfo(RtuDmo con);


}








