package com.logging.task;

import java.util.List;
import java.util.Timer;

public class TimerTaskMuster extends Timer {
	private List<LogTimerTask> taskList;
	
	public TimerTaskMuster(){
		super();
	}
	public TimerTaskMuster(String name){
		super(name);
	}
	
	public List<LogTimerTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<LogTimerTask> taskList) {
		this.taskList = taskList;
	}
	/**
	 * 根据配置的间隔时间启动所有的定时任务
	 */
	public void scheduleLogTask(){
		for(LogTimerTask task : taskList) {
			this.schedule(task, task.getDelayMill(), task.getDelayMill());
		}
	}
	/**
	 * 关闭所有的定时任务, 并关闭该定时任务管理器
	 */
	public void cancelAllTask(){
		for(LogTimerTask task : taskList) {
			task.cancel();
		}
		this.cancel();
	}
}