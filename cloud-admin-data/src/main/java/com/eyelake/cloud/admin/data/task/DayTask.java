package com.eyelake.cloud.admin.data.task;



import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.data.service.intf.DayService;
import com.eyelake.framework.web.util.SpringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author wunder
 * @date 2018年01月22日
 */
public class DayTask extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private static DayService dayService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        TaskExecutor executor = (ThreadPoolTaskExecutor) SpringUtil.getBean("threadPoolTaskExecutor");

        if (null == executor) {
            LOGGER.error("请检查线程池配置文件spring-application-thread.xml...");
            return;
        }

        dayService = (DayService) SpringUtil.getBean("dayService");

        if (null == dayService) {
            LOGGER.error("get dayService bean failed");
            return;
        }

        List<QueryRtuDto> rtuDmoList = dayService.selectRtuList();

        if (CollectionUtils.isNotEmpty(rtuDmoList)) {

            for (QueryRtuDto rtu :rtuDmoList) {
                executor.execute(new DayTaskThread(rtu));
            }
        }
    }

    private class DayTaskThread implements Runnable {

        private QueryRtuDto rtuDto;

        private DayTaskThread(QueryRtuDto rtuDto) {
            this.rtuDto = rtuDto;
        }

        @Override
        public void run() {

                dayService.queryRtuMonthTrafficAndPack(rtuDto);

        }

    }
}
