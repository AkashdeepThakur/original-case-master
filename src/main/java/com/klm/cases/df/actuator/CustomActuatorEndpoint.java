package com.klm.cases.df.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.klm.cases.df.interceptor.ApiMonitor;

/*
 * below class will create  a custom endoint in actuator  to get following metrics 
 */

@Endpoint(id = "apimonitor")
@Component
public class CustomActuatorEndpoint {

	
	@ReadOperation
	public Object customEndpoint() {
		Map<String,Long> map =new HashMap<>(); 
		map.put("TotalRequestProccessed", ApiMonitor.totalRequestPocessed);
		map.put("TotalSuccess", ApiMonitor.totalSuccess);
		map.put("Total4**Error", ApiMonitor.total400);
		map.put("Total5**Error", ApiMonitor.total500);
		map.put("AverageResponseTimeInMilliSec", ApiMonitor.avgResponseTime);
		map.put("MinimumResponseTimInMilliSec", ApiMonitor.minResponseTime);
		map.put("MaximumResponseTimeInMilliSec", ApiMonitor.maxResponseTime);
		map.put("ResponseTimeInMilliSec", ApiMonitor.responseTime);
		
		return map;
		
	}

}
