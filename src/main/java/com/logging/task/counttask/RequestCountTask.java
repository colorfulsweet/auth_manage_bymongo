package com.logging.task.counttask;

import java.util.ArrayList;
import java.util.List;

import com.logging.entity.RequestLog;
import com.logging.service.ILoggingService;
import com.logging.task.LogTimerTask;
import com.system.util.SpringMVCUtils;

/**
 * 执行HTTP请求统计的定时任务
 * @author 结发受长生
 *
 */
public class RequestCountTask extends LogTimerTask {
	private List<RequestLog> requestLogs = new ArrayList<RequestLog>();
	private ILoggingService loggingService;
	
	public RequestCountTask(String taskName, int delayMin){
		super(taskName, delayMin);
	}
	public void addRequestLog(RequestLog requestLog) {
		int index = requestLogs.indexOf(requestLog);
		if(index != -1) {
			RequestLog existLog = requestLogs.get(index);
			int cnt = existLog.getSuccessCount() + existLog.getFailedCount();
			long avgTime = (existLog.getRequestAvgTime() * cnt + requestLog.getRequestAvgTime())/(cnt + 1);
			existLog.setRequestAvgTime(avgTime);
			existLog.setSuccessCount(existLog.getSuccessCount() + requestLog.getSuccessCount());
			existLog.setFailedCount(existLog.getFailedCount() + requestLog.getFailedCount());
		} else {
			requestLogs.add(requestLog);
		}
	}
	@Override
	public void run() {
		if(loggingService == null) {
			loggingService = SpringMVCUtils.getSpringMVCBean("loggingService");
		}
		if(!requestLogs.isEmpty()) {
			log.info("-----保存HTTP响应情况日志信息-----");
			loggingService.saveAllLogs(requestLogs);
		}
		requestLogs.clear();
	}
}
