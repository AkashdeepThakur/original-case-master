package com.klm.cases.df;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BootstrapTest {

	  @LocalServerPort
	    private int port;

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @Test
	    public void testListAirportPagenated() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/klm/list").toString(), String.class);
	        assertNotNull(response.getBody());
	        assertEquals(HttpStatus.OK, response.getStatusCode());

	    }
	    
	    @Test
	    public void testSeachAirportDetails() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/klm/showAirportDetail?airportCode=BBA").toString(), String.class);
	        assertNotNull(response.getBody());
	        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	    }
	    
	    @Test
	    public void testaddAiportList() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/klm/showForm").toString(), String.class);
	        assertNotNull(response.getBody());
	        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	    }
	    
	    @Test
	    public void testSeachAirportDetails_Negative() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/klm/showAirportDetail/").toString(), String.class);
	        assertNotNull(response.getBody());
	        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());

	    }
	    
	    @Test
	    public void searchAirportDisplay() throws Exception {

	        ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/klm/search?theSearchName=SD").toString(), String.class);
	        assertNotNull(response.getBody());
	        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	    }
}
