package com.system.service.aop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * 对MongoDao当中进行的数据库操作进行日志记录的AOP切面
 * @author 结发受长生
 *
 */
@Aspect
@Component
public class MongoDaoAop {
	private static final Logger log = Logger.getLogger(MongoDaoAop.class);
	/**
	 * MongoDao当中与写入数据库有关的方法, 正常返回后调用该方法
	 * @param joinPoint 目标方法的调用情况封装对象
	 * @param returnValue 目标方法的返回值
	 */
	@AfterReturning(value="execution(* com.system.service.dao.MongoDao.*(Object)) ",
					returning="returnValue")
	public void writeReturn(JoinPoint joinPoint, Object returnValue){
		Object item = joinPoint.getArgs()[0];
		String methodName = joinPoint.getSignature().getName();
		log.info("{Entity:" + item.getClass().getSimpleName() + " , type:"+ methodName +"}\n\t" + returnValue);
	}
	/**
	 * MongoDao当中与写入数据库有关的方法, 抛出异常后调用该方法
	 * @param joinPoint 目标方法的调用情况封装对象
	 * @param throwing 错误/异常对象
	 */
	@AfterThrowing(value="execution(* com.system.service.dao.MongoDao.*(Object)) ",throwing="throwing")
	public void writeThrowing(JoinPoint joinPoint, Throwable throwing) {
		Object item = joinPoint.getArgs()[0];
		log.error("{Entity:" + item.getClass().getSimpleName() + "} 写入错误", throwing);
	}
	
	@AfterReturning(value="execution(* com.system.service.dao.MongoDao.dir(..)) ")
	public void dirReturn(JoinPoint joinPoint) {
		Object[] arguments = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		Map<?,?> criteriaMap = null;
		Class<?> clz = null;
		for(Object argument : arguments) {
			if(argument instanceof Map) {
				criteriaMap = (Map<?, ?>) argument;
			} else if(argument instanceof Class) {
				clz = (Class<?>) argument;
			}
		}
		log.info("{Entity:"+ clz.getSimpleName() +" , type:"+methodName+" , criteria:"+criteriaMap+"}");
	}
}
