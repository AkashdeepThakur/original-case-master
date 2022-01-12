package com.klm.cases.df.service;

import com.klm.cases.df.locations.Location;
import com.klm.cases.df.pojo.FairResponce;

public interface RestCallService {
	
	public FairResponce restApiCall(String origin,String destination,String restLink);
	public Location restApiCallSourceDetais(String origin,String restLink);

}
