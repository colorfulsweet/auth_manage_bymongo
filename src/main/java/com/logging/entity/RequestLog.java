package com.logging.entity;

import org.mongodb.morphia.annotations.Entity;

/**
 * HTTP请求的统计日志实体类
 * @author 结发受长生
 *
 */
@Entity("s_log_request")
public class RequestLog extends HttpLogEntry  {
	private static final long serialVersionUID = 7317918720627792650L;
	
	private String requestURI;//请求的URI地址
	private String requestSuffix;//请求的后缀
	private long requestAvgTime;//请求的平均时间
	private int successCount;//成功的数量(2xx,3xx)
	private int failedCount;//失败的数量(4xx,5xx)
	
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getRequestSuffix() {
		return requestSuffix;
	}
	public void setRequestSuffix(String requestSuffix) {
		this.requestSuffix = requestSuffix;
	}
	public long getRequestAvgTime() {
		return requestAvgTime;
	}
	public void setRequestAvgTime(long requestAvgTime) {
		this.requestAvgTime = requestAvgTime;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((requestURI == null) ? 0 : requestURI.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestLog other = (RequestLog) obj;
		if (requestURI == null) {
			if (other.requestURI != null)
				return false;
		} else if (!requestURI.equals(other.requestURI))
			return false;
		return true;
	}
	@Override
	public String toString(){
		return "请求URI地址:"+requestURI+
				"; 平均时间:"+requestAvgTime+
				"; 成功数量:"+successCount+
				"; 失败数量:"+failedCount;
	}
}
