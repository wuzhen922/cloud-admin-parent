package com.eyelake.cloud.admin.system.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.RtuWarningDmo;
import com.eyelake.cloud.admin.assist.dto.account.FlowWarningDto;
import com.eyelake.framework.lang.Result;
import com.yjh.framework.page.Page;


import java.util.List;

public interface FlowWarningService {

    public List<FlowWarningDto> queryFlowListByPage(FlowWarningDto queryDto, Page page);
    public Result settingFlowWarning(RtuWarningDmo rtuWarningDmo);
}
