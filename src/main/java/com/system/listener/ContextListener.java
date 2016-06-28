package com.system.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

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
 * Context监听器, 加载Spring配置以及appConfig中的若干应用配置项
 * @author 结发受长生
 *
 */
@WebListener
public class ContextListener extends ContextLoaderListener {
	private static Logger log = Logger.getLogger(ContextListener.class);
	
	private Properties props;
	
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
		String configPath = context.getInitParameter("configPath");
		props = new Properties();
		InputStream input = ContextListener.class.getResourceAsStream(configPath);
		if(input == null){
			log.error("配置文件读取失败 : "+configPath);
			return;
		}
		try {
			props.load(input);
			Set<Entry<Object, Object>> entries = props.entrySet();
			log.info("===加载应用程序配置项===");
			for(Entry<Object, Object>entry : entries) {
				log.info(entry.getKey() + " : " + entry.getValue());
				context.setAttribute(entry.getKey().toString(), entry.getValue());
			}
		} catch (IOException e) {
			log.error("加载配置文件出错!",e);
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
