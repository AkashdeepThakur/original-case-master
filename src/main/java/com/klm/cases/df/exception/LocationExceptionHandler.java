package com.klm.cases.df.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/*
 * class to handle  exception and redirect accordingly
 */

@ControllerAdvice
@Slf4j
public class LocationExceptionHandler {
	
	UUID uniqueKey; 
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
	public String handleException(Model model, LocationNotFound exe) {
		LocationErrorResponse errorResponse = new LocationErrorResponse(HttpStatus.BAD_GATEWAY.value(),
				exe.getMessage(), System.currentTimeMillis());
		uniqueKey = UUID.randomUUID();  
		log.error("TransactionId : "+uniqueKey+" Error Occur : " + exe.getMessage());
		model.addAttribute("errorResponce", errorResponse);
		return "errorPageApiDown";
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
	public String handleException(Model model, Exception exe) {
		LocationErrorResponse errorResponse = new LocationErrorResponse(HttpStatus.BAD_GATEWAY.value(),
				exe.getMessage(), System.currentTimeMillis());
		uniqueKey = UUID.randomUUID();  
		log.error("TransactionId : "+uniqueKey+" Error Occur : " + exe.getMessage());
		model.addAttribute("errorResponce", errorResponse);
		return "errorPageApiDown";
	}

}
