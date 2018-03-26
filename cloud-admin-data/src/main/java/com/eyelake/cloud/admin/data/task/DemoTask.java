package com.eyelake.cloud.admin.data.task;

import com.eyelake.cloud.admin.data.service.intf.DemoService;
import com.eyelake.framework.web.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Random;

/**
 * @author wunder
 * @date 2018年01月22日
 */
public class DemoTask extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoTask.class);

    private static DemoService demoService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        TaskExecutor executor = (ThreadPoolTaskExecutor) SpringUtil.getBean("threadPoolTaskExecutor");

        if (null == executor) {
            LOGGER.error("请检查线程池配置文件spring-application-thread.xml...");
            return;
        }

        demoService = (DemoService) SpringUtil.getBean("demoService");

        if (null == demoService) {
            LOGGER.error("get demoService bean failed");
            return;
        }

        for (int i = 0; i < 1000; i++) {
            executor.execute(new TaskThread(String.valueOf(i)));
        }

    }

    private class TaskThread implements Runnable {

        private String name;

        public TaskThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {

            Random random = new Random();

            int time = random.nextInt(1000);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            demoService.print(name);
        }

    }
}
