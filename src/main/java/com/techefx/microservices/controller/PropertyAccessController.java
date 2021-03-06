package com.techefx.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techefx.microservices.beans.PropertyAccessBean;
import com.techefx.microservices.beans.PropertyAccessValue;

@RestController
@RequestMapping("/access")
public class PropertyAccessController {

	@Autowired
	PropertyAccessBean propertyAccessBean;

	@GetMapping("/property")
	public PropertyAccessValue getProperties() {

		refreshActuator();
		return new PropertyAccessValue(propertyAccessBean.getName(), propertyAccessBean.getDescription());

	}

	public void refreshActuator() {

		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8100/actuator/refresh";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> results = restTemplate.postForEntity(baseUrl, entity, String.class);

	}
}
