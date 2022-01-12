package com.klm.cases.df.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDetails {
	
	private String origin;
	private String destination;
	public FlightDetails() {}
	
	public FlightDetails(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}

}
