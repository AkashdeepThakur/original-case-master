package com.klm.cases.df.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.klm.cases.df.exception.LocationNotFound;
import com.klm.cases.df.locations.Location;
import com.klm.cases.df.pojo.FairResponce;

/*
 * Task for this service to call the back end api can consume the data
 * 
 */
@Service
public class RestCallServiceImpl implements RestCallService {

	@Value("${username.password}")
	String usernamePassword;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FairResponce restApiCall(String origin, String destination, String restLink) {
		FairResponce fairResponce = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String authStr = usernamePassword;
			String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			HttpEntity request = new HttpEntity(headers);
			ResponseEntity<FairResponce> response = restTemplate.exchange(restLink + "/{src}/{dst}", HttpMethod.GET,
					request, FairResponce.class, origin, destination);
			fairResponce = response.getBody();
		} catch (Exception e) {
			throw new LocationNotFound("BackEnd Api is down ");
		}
		return fairResponce;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Location restApiCallSourceDetais(String origin, String restLink) {
		Location location = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String authStr = usernamePassword;
			String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			HttpEntity request = new HttpEntity(headers);
			ResponseEntity<Location> response = restTemplate.exchange(restLink + "/{src}", HttpMethod.GET, request,
					Location.class, origin);
			location = response.getBody();
		} catch (Exception e) {
			throw new LocationNotFound("BackEnd Api is down ");
		}
		return location;

	}

}
