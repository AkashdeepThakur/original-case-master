package com.klm.cases.df.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.klm.cases.df.Bootstrap;
import com.klm.cases.df.locations.FetchAirportDetails;
import com.klm.cases.df.locations.Location;
import com.klm.cases.df.pojo.FairResponce;
import com.klm.cases.df.pojo.FlightDetails;
import com.klm.cases.df.pojo.FlightDetailsInput;
import com.klm.cases.df.service.LocationPagination;
import com.klm.cases.df.service.RestCallService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class will be a controller for our application 
 * 1. listairportPagenated() will be the first method to execute and it will list down all the  airport we got from back end  and  paginate them 
 * 2. seachAirportDetails() will call backend API to fetch the individual details of an Airport 
 * 3. listEmployees() method will help us to assign the   object to form and  list of all airpport for search purpose  
 * 4. showFair() method will fetch the fair as per the  source and destination enter and will redirect to display
 */

@Controller
@RequestMapping("/klm")
@Slf4j
public class MainController {

	@Value("${resturl.tocallsrcdst}")
	String airportsFair;

	@Value("${resturl.tocallsrc}")
	String airportDetail;

	@Autowired
	LocationPagination locationPagination;

	@Autowired
	RestCallService restCallService;
	
	UUID uniqueKey;   
	 

	@GetMapping("/list")
	@ResponseStatus(value = HttpStatus.OK)
	public String listairportPagenated(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {
		Map<String, Location> airportlist = FetchAirportDetails
				.getAirportLocationMap(Bootstrap.airports.getEmbedded().getLocations());
		List<Location> locations = new ArrayList<Location>();
		airportlist.forEach((k, v) -> locations.add(v));
		Page<Location> locationPaginating = locationPagination.findPaginated(PageRequest.of(page - 1, size), locations);
		model.addAttribute("locations", locationPaginating);

		int totalPages = locationPaginating.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		uniqueKey = UUID.randomUUID();  
		log.info("TransactionId : "+uniqueKey+" Airport List displaying");
		return "list-airport";

	}

	@GetMapping("/showAirportDetail")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String seachAirportDetails(@RequestParam("airportCode") String airportCode, Model model) {
		Location location = restCallService.restApiCallSourceDetais(airportCode, airportDetail);
		model.addAttribute("location", location);
		uniqueKey = UUID.randomUUID();  
		log.info("TransactionId : "+uniqueKey+" airport detail displaying");
		return "airport-details";
	}

	@GetMapping("/showForm")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String addAiportList(Model model) {

		// List<Employee> employees = employeeService.findAll();
		List<String> airportlist = FetchAirportDetails
				.getAirportCodeList(Bootstrap.airports.getEmbedded().getLocations());
		FlightDetailsInput flightDetailsInput = new FlightDetailsInput();
		model.addAttribute("flightDetails", flightDetailsInput);
		model.addAttribute("origin", airportlist);
		uniqueKey = UUID.randomUUID();  
		log.info("TransactionId : "+uniqueKey+" request to add list to and object to hold data for source and destination selection");
		return "source-destination-form";
	}

	@PostMapping("/save")
	@ResponseStatus(value = HttpStatus.OK)
	public String showFair(@ModelAttribute("flightDetails") FlightDetails flightDetails, Model model) {
		Map<String, String> airportlist = FetchAirportDetails
				.getAirportDetails(Bootstrap.airports.getEmbedded().getLocations());
		Map<String, Location> locationsMap = FetchAirportDetails
				.getAirportLocationMap(Bootstrap.airports.getEmbedded().getLocations());
		FairResponce fairResponce = restCallService.restApiCall(airportlist.get(flightDetails.getOrigin()),
				airportlist.get(flightDetails.getDestination()), airportsFair);
		fairResponce.setFair(fairResponce.getAmount() + fairResponce.getCurrency());
		fairResponce
				.setOrigin(locationsMap.get(fairResponce.getOrigin()).getName() + "(" + fairResponce.getOrigin() + ")");
		fairResponce.setDestination(
				locationsMap.get(fairResponce.getDestination()).getName() + "(" + fairResponce.getDestination() + ")");
		model.addAttribute("fairResponce", fairResponce);
		uniqueKey = UUID.randomUUID();  
		log.info("TransactionId : "+uniqueKey+" Fair displayed for"+fairResponce.getOrigin()+":"+fairResponce.getOrigin());
		return "fair-details";
	}
	

	@GetMapping("/search")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String searchAirportDisplay(@RequestParam("theSearchName") String name,Model model) {
		List<String> airportlistcode = FetchAirportDetails
				.getAirportCodeList(Bootstrap.airports.getEmbedded().getLocations());
		Map<String, String> airportlistdetails = FetchAirportDetails
				.getAirportDetails(Bootstrap.airports.getEmbedded().getLocations());
		Map<String, Location> locationsMap = FetchAirportDetails
				.getAirportLocationMap(Bootstrap.airports.getEmbedded().getLocations());
		Set<Location> locations = new HashSet<Location>();
		
		airportlistcode.stream().forEach(
	            (temp) ->{
	            	if(temp.toLowerCase().contains(name.toLowerCase())) {
	            		locations.add(locationsMap.get(airportlistdetails.get(temp)));
	            	}
	            });		
		
		model.addAttribute("locations", locations);
		uniqueKey = UUID.randomUUID();  
		log.info("TransactionId : "+uniqueKey+" request to search the  airport");
		return "list-airport-search";
	}
}
