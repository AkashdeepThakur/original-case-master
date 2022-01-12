package com.klm.cases.df.locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FetchAirportDetails {

	public static List<String> airportListCode = new ArrayList<>();
	public static Map<String, String> airportList = new HashMap<>();
	public static Map<String, Location> locationsMap = new HashMap<>();

	public static List<String> fethAirport(List<Location> locations) {
		for (Iterator<Location> iterator = locations.iterator(); iterator.hasNext();) {
			Location location = (Location) iterator.next();
			airportListCode.add(location.getCode());
			airportListCode.add(location.getName());
			airportListCode.add(location.getDescription());

			airportList.put(location.getCode(), location.getCode());
			airportList.put(location.getName(), location.getCode());
			airportList.put(location.getDescription(), location.getCode());
			locationsMap.put(location.getCode(), location);
		}
		return airportListCode;
	}

	public static List<String> getAirportCodeList(List<Location> locations) {

		if (airportListCode.isEmpty()) {
			fethAirport(locations);
		}
		return airportListCode;
	}

	public static Map<String, String> getAirportDetails(List<Location> locations) {

		if (airportList.isEmpty()) {
			fethAirport(locations);
		}
		return airportList;
	}

	public static Map<String, Location> getAirportLocationMap(List<Location> locations) {

		if (locationsMap.isEmpty()) {
			fethAirport(locations);
		}
		return locationsMap;
	}

}
