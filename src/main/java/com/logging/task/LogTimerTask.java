package com.logging.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public abstract class LogTimerTask extends TimerTask {
	protected static Logger log = Logger.getLogger(LogTimerTask.class);
	private static int count = 1;
	/**
	 * 任务名称
	 */
	protected String taskName;
	/**
	 * 执行的间隔时间
	 */
	protected int delayMin;
	protected LogTimerTask(int delayMin){
		this("日志定时任务" + count++, delayMin);
	}
	protected LogTimerTask(String taskName, int delayMin){
		this.taskName = taskName;
		this.delayMin = delayMin;
	}
	/**
	 * 获取定时任务间隔的毫秒数
	 * @return 毫秒数
	 */
	public long getDelayMill(){
		return 1000L * 60 * delayMin;
	}
}
