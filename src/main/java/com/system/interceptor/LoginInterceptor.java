package com.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.logging.entity.RequestLog;
import com.logging.task.counttask.RequestCountTask;
import com.system.util.SpringUtils;
/**
 * 验证登陆状态的拦截器
 * @author 结发受长生
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	//超时页面, 执行刷新操作
	private final String TIMEOUT_PAGE = "/WEB-INF/error_pages/timeout.jsp";
	private final String START_MARK = "RequestStartNanoTime";
	
	private final RequestCountTask requestCountTask;
	public LoginInterceptor(){
		requestCountTask = SpringUtils.getBean("requestCountTask");
	}
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response,
			Object obj) throws Exception {
		if(request.getSession().getAttribute("user") == null){
			//如果用户未登录或登陆已超时 ,则终止该请求 ,并将请求转发到登陆超时页面
			request.getRequestDispatcher(TIMEOUT_PAGE).forward(request, response);
			return false;
		}
		request.setAttribute(START_MARK, System.nanoTime());
		return true;
	}
	/**
	 * 到达目标控制器之后(视图渲染之前)执行该方法
	 */
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response,
			Object obj, 
			ModelAndView model) throws Exception {
		
	}
	
	/**
	 * 在整个请求完成之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response,
			Object obj,
			Exception ecp) throws Exception {
		long start = (Long) request.getAttribute(START_MARK);
		//HTTP请求完成的总耗时
		long requestCompleteTime = (System.nanoTime() - start)/1000;
		
		RequestLog requestLog = new RequestLog();
		String uri = request.getRequestURI();
		requestLog.setRequestURI(uri);
		requestLog.setRequestSuffix(uri.substring(uri.lastIndexOf('.') + 1));
		if(response.getStatus() >= 400) {
			requestLog.setFailedCount(1);
		} else {
			requestLog.setSuccessCount(1);
		}
		requestLog.setRequestAvgTime(requestCompleteTime);
		requestCountTask.addRequestLog(requestLog);
	}
}
