package com.klm.cases.df.actuator;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.klm.cases.df.service.RestCallService;


/*
 * Class to check whether backend api is  running or down
 * 
 */
@Component
public class RestApiServiceCheck  implements HealthIndicator{
 
	@Autowired
	RestCallService restCallService;
	
	@Value("${resturl.tolistairports}")
	String listAirport;
	
	@Value("${username.password}")
	String usernamePassword;
	
	private String restApiService = "RestApiUpOrDown";
 
	@Override
	public Health health() {
		// TODO Auto-generated method stub
		if(isApiUpDOwn())
			return Health.up().withDetail(restApiService, "Working").build();
		return Health.down().withDetail(restApiService, "NotWorking").build();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isApiUpDOwn() {
		//write logic to chck Api
		try {
			RestTemplate restTemplate = new RestTemplate();
			String authStr = usernamePassword;
			String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			HttpEntity request = new HttpEntity(headers);
			ResponseEntity<String> response = restTemplate.exchange(listAirport, HttpMethod.GET,
					request, String.class);
			String str = response.getBody();
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

}
