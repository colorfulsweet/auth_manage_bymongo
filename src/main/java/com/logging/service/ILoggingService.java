package com.logging.service;

import java.util.List;

import com.logging.entity.HttpLogEntry;

public interface ILoggingService {
	/**
	 * 保存所有的HTTP日志信息
	 * @param requestLogs
	 */
	public void saveAllLogs(List<? extends HttpLogEntry> requestLogs);
}
