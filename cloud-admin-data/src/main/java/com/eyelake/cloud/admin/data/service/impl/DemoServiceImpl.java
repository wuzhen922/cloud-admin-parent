package com.eyelake.cloud.admin.data.service.impl;

import com.eyelake.cloud.admin.data.service.intf.DemoService;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.utils.DateUtil;
import org.springframework.stereotype.Service;

/**
 * @author wunder
 * @date 2018年01月22日
 */
@Service("demoService")
@ServiceTrace
public class DemoServiceImpl implements DemoService {


    @Override
    public void print(String name) {
        System.out.println("call thread "+name+" at time "+ DateUtil.format(DateUtil.getDate(),DateUtil
                .yyyy_MM_dd_HH_mm_ss_SSS));
    }

}
