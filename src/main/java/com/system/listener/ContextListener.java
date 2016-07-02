package com.system.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.util.Log4jConfigListener;

import com.logging.task.TimerTaskMuster;
//import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;
import com.system.util.SpringUtils;

/**
 * Context监听器, 加载Spring配置以及web.xml中的若干应用配置项
 * @author 结发受长生
 *
 */
@WebListener
public class ContextListener extends ContextLoaderListener {
	private static Logger log = Logger.getLogger(ContextListener.class);
	
	private Log4jConfigListener log4jConfig;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		//log4j监听器
		log4jConfig = SpringUtils.getBean(Log4jConfigListener.class);
		//web service监听器
		log4jConfig.contextInitialized(event);
		
		ServletContext context = event.getServletContext();
		//读取应用的基本配置信息
		String[] paramNames = {"project_name", "app_name", "icon_path"};
		log.info("===加载应用程序基本信息===");
		for(String paramName : paramNames) {
			String param = context.getInitParameter(paramName);
			context.setAttribute(paramName, param);
			log.info(paramName + " : " + param);
		}
		
		this.loadTimerTask();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		this.unloadTimerTask();
		log4jConfig.contextDestroyed(event);
		log4jConfig = null;
		super.contextDestroyed(event);
	}
	/**
	 * 加载定时任务
	 */
	private void loadTimerTask(){
		TimerTaskMuster taskMuster = SpringUtils.getBean(TimerTaskMuster.class);
		taskMuster.scheduleLogTask();
	}
	/**
	 * 卸载定时任务
	 */
	private void unloadTimerTask(){
		TimerTaskMuster taskMuster = SpringUtils.getBean(TimerTaskMuster.class);
		taskMuster.cancelAllTask();
	}
}
