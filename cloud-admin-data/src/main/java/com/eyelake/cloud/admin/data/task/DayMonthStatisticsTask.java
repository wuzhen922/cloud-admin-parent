package com.eyelake.cloud.admin.data.task;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.OwnerDmo;
import com.eyelake.cloud.admin.data.service.intf.DayMonthStatisticsService;
import com.eyelake.cloud.admin.data.service.intf.DemoService;
import com.eyelake.framework.web.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 天月统计定时任务
 * Created by wff on 2018/1/24.
 */
public class DayMonthStatisticsTask extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayMonthStatisticsTask.class);

    private static DayMonthStatisticsService dayMonthStatisticsService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        TaskExecutor executor = (ThreadPoolTaskExecutor) SpringUtil.getBean("threadPoolTaskExecutor");

        if (null == executor) {
            LOGGER.error("请检查线程池配置文件spring-application-thread.xml...");
            return;
        }

        dayMonthStatisticsService = (DayMonthStatisticsService) SpringUtil.getBean("dayMonthStatisticsService");

        if (null == dayMonthStatisticsService) {
            LOGGER.error("get dayMonthStatisticsService bean failed");
            return;
        }

        List<OwnerDmo> ownerDmoList = dayMonthStatisticsService.queryOwnerList();
        List<IntegratorDmo> integratorDmoList = dayMonthStatisticsService.queryIntegratorList();

        for (OwnerDmo owner : ownerDmoList) {
            executor.execute(new OwnerTaskThread(owner));
        }

        for (IntegratorDmo integratorDmo : integratorDmoList){
            executor.execute(new IntegratorTaskThread(integratorDmo));
        }

        executor.execute(new SystemTaskThread());

    }

    private class OwnerTaskThread implements Runnable {

        private OwnerDmo ownerDmo;

        public OwnerTaskThread (OwnerDmo owner){
            this.ownerDmo = owner;
        }
        @Override
        public void run() {

            dayMonthStatisticsService.dayMonthOwnerStatistics(ownerDmo);
        }
    }

    private class IntegratorTaskThread implements Runnable{
        private IntegratorDmo integratorDmo;

        public IntegratorTaskThread (IntegratorDmo integratorDmo){
            this.integratorDmo = integratorDmo;
        }

        @Override
        public void run() {
            dayMonthStatisticsService.dayMonthIntegratorStatistics(integratorDmo);
        }
    }

    private class SystemTaskThread implements Runnable{

        @Override
        public void run() {
            dayMonthStatisticsService.dayMonthSystemStatistics();
        }
    }
}
