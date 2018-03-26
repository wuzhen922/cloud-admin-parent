package com.eyelake.cloud.admin.data.task;



import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.data.service.intf.RtuPackService;
import com.eyelake.framework.web.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import java.util.List;



public class RtuPackTask extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RtuPackTask.class);

    private static RtuPackService rtuPackService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        TaskExecutor executor = (ThreadPoolTaskExecutor) SpringUtil.getBean("threadPoolTaskExecutor");

        if (null == executor) {
            LOGGER.error("请检查线程池配置文件spring-application-thread.xml...");
            return;
        }

        rtuPackService = (RtuPackService) SpringUtil.getBean("rtuPackService");


        if (null == rtuPackService) {
            LOGGER.error("get rtuPackService or rtuService bean failed");
            return;
        }


        //查询正常或禁用的rtuDmoList,循环遍历，执行线程
        List<QueryRtuDto> rtuDmoList = rtuPackService.queryRtuByStatus();

        if (CollectionUtils.isEmpty(rtuDmoList)) {

            LOGGER.info("查询到正常或禁用状态的网关为空");
            return;
        }

        for (QueryRtuDto rtu : rtuDmoList) {

            executor.execute(new RtuPackTask.rtuPackTaskThread(rtu.getSnNumber()));
        }

    }

    private class rtuPackTaskThread implements Runnable {

        private String snNumber;

        public rtuPackTaskThread(String snNumber) {
            this.snNumber = snNumber;
        }

        //每个SN码需要对应的动作
        @Override
        public void run() {

            rtuPackService.rtuPackInfo(snNumber);

        }

    }







}
