package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuWarningDmo;
import com.eyelake.cloud.admin.assist.dto.account.FlowWarningDto;
import com.eyelake.cloud.admin.system.service.intf.FlowWarningService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.eyelake.framework.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("flowWarningService")
@ServiceTrace
public class FlowWarningServiceImpl implements FlowWarningService {
    private static Logger logger = LoggerFactory.getLogger(FlowWarningServiceImpl.class);
    @Autowired
    private CommonDao commonDao;

    @Override
    public List<FlowWarningDto> queryFlowListByPage(FlowWarningDto queryDto, Page page) {
        List<FlowWarningDto> flowWarningDto = commonDao.selectListByPage("FlowWarningMapper.countWarning", "FlowWarningMapper.queryFlowList", queryDto, page);
        return flowWarningDto;
    }

    @Override
    public Result settingFlowWarning(RtuWarningDmo con) {

        Result result = new Result();

        RtuWarningDmo rtuWarningDmo = new RtuWarningDmo();
//        检查该rtu是否存在
        rtuWarningDmo.setSnNumber(con.getSnNumber());
        rtuWarningDmo.setStatus(Constants.RtuWarningTableStatus.NORMAL);
        RtuWarningDmo existRtu = (RtuWarningDmo)commonDao.selectOne(rtuWarningDmo);
        if(null == existRtu){
            result.fail("","该数据已被删除，更新失败！");
            return result;
        }

        //        更新rtu信息
        if(StringUtils.isBlank(String.valueOf(con.getWarningNum()))){
            result.fail("","阈值为空，更新失败！");
            return result;
        }

        //更新预警状态
        Double  costNum =existRtu.getCostNum();//kb
        Long packTraffic =existRtu.getPackTraffic();//mb
        Double warningNum = con.getWarningNum();//kb
        if((packTraffic *1024) <costNum){
            rtuWarningDmo.setWarningStatus(Constants.FlowWarningStatus.BEYOND);
        }else if(costNum > warningNum){
            rtuWarningDmo.setWarningStatus(Constants.FlowWarningStatus.WARNING);
        }else {
            rtuWarningDmo.setWarningStatus(Constants.FlowWarningStatus.NORMAL);
        }
        rtuWarningDmo.setWarningNum(warningNum);
        rtuWarningDmo.setLastUpdateTime(DateUtil.getDate());

        int i = commonDao.update("FlowWarningMapper.updateWarning",rtuWarningDmo);
        if(i<1){
            result.fail("","更新流量阈值信息失败！");
            return result;
        }

        result.setSuccess(true);
        return result;
    }
}
