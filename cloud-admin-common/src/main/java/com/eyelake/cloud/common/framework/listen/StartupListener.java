package com.eyelake.cloud.common.framework.listen;

import com.eyelake.cloud.common.constants.ApplicationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * StartupListener
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    public void contextInitialized(ServletContextEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("initializing context...");
        }
        // call Spring's context ContextLoaderListener to initialize
        super.contextInitialized(event);
        ServletContext application = event.getServletContext();
        //设置系统启动时间
        application.setAttribute(ApplicationKey.SYSTEM_STARTUP_TIME, new Date().getTime());
        //在线用户人数（已登录，未登录）。
        //application.setAttribute(ApplicationKey.ONLINE_USER_COUNT, 0);
        //已登录用户
        //application.setAttribute(ApplicationKey.LOGIN_USER_MAP, new HashMap<String,String>());
        //getStartServiceImple(context).processAppInit(context);
    }
}