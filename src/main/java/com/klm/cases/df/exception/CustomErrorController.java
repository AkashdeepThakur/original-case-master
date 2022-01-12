package com.klm.cases.df.exception;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomErrorController extends AbstractErrorController {
    private static final String ERROR_PATH=  "/error";

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * Just catching the {@linkplain LocationNotFound} exceptions and render
     * the 404.html error page.
     */
    @ExceptionHandler(LocationNotFound.class)
    public String notFound() {
    	UUID uniqueKey = UUID.randomUUID();  
		log.error("TransactionId : "+uniqueKey+" URL is not correct");
    	 return "errorPage";
    }

    /**
     * Responsible for handling all errors and throw especial exceptions
     * for some HTTP status codes. Otherwise, it will return a map that
     * ultimately will be converted to a json error.
     */
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<?> handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if (status.equals(HttpStatus.NOT_FOUND))
            throw new LocationNotFound();

        return ResponseEntity.status(status).body(getErrorAttributes(request, false));
    }

    private Object getErrorAttributes(HttpServletRequest request, boolean b) {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}

	public String getErrorPath() {
        return ERROR_PATH;
    }
}
