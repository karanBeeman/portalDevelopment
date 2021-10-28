package com.amazon.portaldevelopment.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;
import com.amazon.portaldevelopment.exceptions.ServiceException;

@Service
public class LoginServiceImpl {

	@Autowired
	WebClient.Builder webClient;

	@Autowired
	ConfigurationPropertiesClass config;

	public String getUserAccessId(String id) {
		try {
			WebClient client = webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
			String response = client.get().uri(uriBuilder -> uriBuilder.path("user/" + id)
					.queryParam("apiKey", config.getWorkFrontApiKey())
					.queryParam("fields", "accessLevelID").build())
					.retrieve().bodyToMono(String.class)
					.block();
			return response;
		} catch (WebClientResponseException e) {
			throw new ServiceException(e.getMessage(), e.getRawStatusCode());
		}
	}

	public String getUserRole(String accessId) {
		try {
			WebClient client = webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
			String response = client.get()
					.uri(uriBuilder -> uriBuilder.path("acslvl/" + accessId)
					.queryParam("apiKey", config.getWorkFrontApiKey())
					.queryParam("fields", "name")
					.build())
					.retrieve()
					.bodyToMono(String.class)
					.block();
			return response;
		} catch (WebClientResponseException e) {
			throw new ServiceException(e.getMessage(), e.getRawStatusCode());
		}
	}
}
