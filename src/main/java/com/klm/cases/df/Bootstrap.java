package com.klm.cases.df;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.klm.cases.df.locations.Airports;

@SpringBootApplication
public class Bootstrap {

	@Value("${resturl.tolistairports}")
	String listAirport;

	public static Airports airports;

	public static void main(final String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

	/*
	 * Below methods will connect to the backend Api to consume the Airport list and retrieve it.
	 * 
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.basicAuthentication("user", "secret123").build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			airports = restTemplate.getForObject(listAirport, Airports.class);

		};
	}

	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
