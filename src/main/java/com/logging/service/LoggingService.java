package com.logging.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logging.entity.HttpLogEntry;
import com.system.service.dao.IMongoDao;

@Service
public class LoggingService implements ILoggingService {
	private static Logger log = Logger.getLogger(LoggingService.class);
	
	@Autowired
	private IMongoDao mongoDao;
	
	@Override
	public void saveAllLogs(List<? extends HttpLogEntry> httpLogs) {
		Date now = new Date();
		for(HttpLogEntry logEntry : httpLogs){
			logEntry.setCreateDate(now);
			log.info(logEntry);
			mongoDao.save(logEntry);
		}
	}
}
