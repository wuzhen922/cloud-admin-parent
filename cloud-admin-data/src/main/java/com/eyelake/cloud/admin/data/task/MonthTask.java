package com.eyelake.cloud.admin.data.task;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.OwnerDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.data.service.intf.MonthService;
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
 * @author xudajan
 * @date 2018.1.25
 */
public class MonthTask extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonthTask.class);

    private static MonthService monthService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        TaskExecutor executor = (ThreadPoolTaskExecutor) SpringUtil.getBean("threadPoolTaskExecutor");

        if (null == executor) {
            LOGGER.error("请检查线程池配置文件spring-application-thread.xml...");
            return;
        }

        monthService = (MonthService) SpringUtil.getBean("monthService");

        if (null == monthService) {
            LOGGER.error("get monthService bean failed");
            return;
        }

        List<RtuDmo> rtuDmoList = monthService.queryRtuList();

        for (RtuDmo rtuDmo : rtuDmoList) {
            executor.execute(new RtuMonthTaskThread(rtuDmo));
        }

        List<OwnerDmo> ownerDmoList = monthService.queryOwnerList();

        for (OwnerDmo ownerDmo : ownerDmoList) {
            executor.execute(new OwnerMonthTaskThread(ownerDmo));
        }

        List<IntegratorDmo> integratorDmoList = monthService.queryIntegratorList();

        for (IntegratorDmo integratorDmo : integratorDmoList) {
            executor.execute(new IntegratorMonthTaskThread(integratorDmo));
        }

            executor.execute(new SystemMonthTaskThread());

    }

    private class RtuMonthTaskThread implements Runnable {

        private RtuDmo rtuDmo;

        public RtuMonthTaskThread (RtuDmo rtuDmo){
            this.rtuDmo = rtuDmo;
        }
        @Override
        public void run() {
            monthService.rtuYearTrafficTable(rtuDmo);
        }
    }

    private class OwnerMonthTaskThread implements Runnable {

        private OwnerDmo ownerDmo;

        public OwnerMonthTaskThread (OwnerDmo ownerDmo){
            this.ownerDmo = ownerDmo;
        }
        @Override
        public void run() {
            monthService.ownerYearTrafficTable(ownerDmo);
        }
    }

    private class IntegratorMonthTaskThread implements Runnable{
        private IntegratorDmo integratorDmo;

        public IntegratorMonthTaskThread (IntegratorDmo integratorDmo){
            this.integratorDmo = integratorDmo;
        }

        @Override
        public void run() {
            monthService.integratorYearTrafficTable(integratorDmo);
        }
    }

    private class SystemMonthTaskThread implements Runnable{

        @Override
        public void run() {
            monthService.systemYearTrafficTable();
        }
    }

}
