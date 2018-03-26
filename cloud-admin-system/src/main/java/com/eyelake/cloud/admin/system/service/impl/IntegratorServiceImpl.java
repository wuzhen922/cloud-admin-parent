package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorDto;
import com.eyelake.cloud.admin.assist.result.DownloadRtuResult;
import com.eyelake.cloud.admin.system.service.intf.IntegratorService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.utils.SqlAssertUtils;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * IntegratorService接口实现类
 *
 * @author  j_cong
 * @date    2017/12/14
 * @version V1.0
 */

@Service("integratorService")
@ServiceTrace
public class IntegratorServiceImpl implements IntegratorService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<QueryIntegratorDto> selectListByPage(QueryIntegratorDto con, Page page) {

        return commonDao.selectListByPage("IntegratorMapper.queryIntegratorCount",
                "IntegratorMapper.queryIntegratorList", con, page);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result changeIntegratorStatus(IntegratorDmo con) {

        Result result = new Result();

        String status = con.getStatus();
        con.setLastUpdateTime(DateUtil.getDate());

            //如果集成商状态为“正常”，则进入的操作为“禁用”
            //如果集成商状态为“禁用”，则进入的操作为“启用”
            if (Constants.IntegratorStatus.NORMAL.equals(status)) {
                con.setStatus(Constants.IntegratorStatus.DISABLED);
            } else {
                con.setStatus(Constants.IntegratorStatus.NORMAL);
            }

            //更改集成商的状态
            int i = commonDao.update("IntegratorMapper.changeIntegratorStatus", con);

            if (i < 0) {
                result.fail();
                result.setMessage("更改集成商状态失败");
                return result;
            }

            //关联更新集成商下的RTU、业主企业、传感器
            commonDao.update("IntegratorMapper.changeRtuStatus", con);
            commonDao.update("IntegratorMapper.changeSensorStatus", con);
            commonDao.update("IntegratorMapper.changeOwnerStatus", con);

        return result;
    }

    @Override
    public Result deleteIntegrator(IntegratorDmo con) {

        Result result = new Result();

        int i = commonDao.update("IntegratorMapper.deleteIntegrator", con);

        if (i < 0) {
            result.fail();
            result.setMessage("删除集成商失败");
            return result;
        }

        //关联软删除集成商下的RTU、业主、企业
        commonDao.update("IntegratorMapper.deleteRtu", con);
        commonDao.update("IntegratorMapper.deleteSensor", con);
        commonDao.update("IntegratorMapper.deleteOwner", con);

        return result;
    }

    @Override
    public List<IntegratorDmo> selectList(IntegratorDmo con) {

        return commonDao.selectList("IntegratorMapper.selectList",con);

    }

    @Override
    public DownloadRtuResult downloadRtuInfo(IntegratorDmo con) {

        DownloadRtuResult result = new DownloadRtuResult();
        String[] headers = new String[]{"网关名称","网关型号","网关传输类型","网关接入类型","网关序列码","所属运营商"};

        List<LinkedHashMap<String, String>> contentList = commonDao.selectList("IntegratorMapper.exportRtuInfo", con);
        result.setHeaders(headers);
        result.setContentList(contentList);

        return result;

    }

}
