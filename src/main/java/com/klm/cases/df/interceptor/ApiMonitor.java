package com.klm.cases.df.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * ApiMonitor class will create a ccustom endpoint to get details  regarding total reuest processed and 
 * total 400 500 etc error avg,min and max response time 
 * 
 */

@Component
public class ApiMonitor implements HandlerInterceptor {

	public static long totalRequestPocessed;
	public static long totalSuccess;
	public static long total400;
	public static long total500;
	public static long avgResponseTime;
	public static long minResponseTime;
	public static long maxResponseTime;
	public static long responseTime;
	long timestamp;

	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler) throws Exception { // TODO
	 * Auto-generated method stub return HandlerInterceptor.super.preHandle(request,
	 * response, handler); }
	 */

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		responseTime = System.currentTimeMillis();
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		totalRequestPocessed++;
		responseTime = System.currentTimeMillis() - responseTime;

		if (minResponseTime == 0 || responseTime < minResponseTime)
			minResponseTime = responseTime;
		if (responseTime > maxResponseTime)
			maxResponseTime = responseTime;
		if (avgResponseTime != 0)
			avgResponseTime = (avgResponseTime + responseTime) / 2;
		else
			avgResponseTime = responseTime;

		int status = Integer.parseInt(Integer.toString(response.getStatus()).substring(0, 1));
		switch (status) {
		case 4:
			total400++;
			break;
		case 5:
			total500++;
			break;
		default:
			totalSuccess++;
			break;
		}
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
