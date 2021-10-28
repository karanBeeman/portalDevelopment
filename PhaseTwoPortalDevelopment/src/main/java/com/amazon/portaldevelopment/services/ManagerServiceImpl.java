package com.amazon.portaldevelopment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;
import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.exceptions.ServiceException;

@Service
public class ManagerServiceImpl extends ManagerService{
	
	@Autowired
	WebClient.Builder webClientInstance;

	@Autowired
	ConfigurationPropertiesClass configuration;

	public String getTimeOffDetails(String fields) {
		try{
         	WebClient client =	webClientInstance.baseUrl(configuration.getWorkFrontBaseUrl()).build();
         	String projectList = client.get().uri(uriBuilder -> uriBuilder
         			.path("user/search")
         			.queryParam(ConstantValues.API_KEY, configuration.getWorkFrontApiKey())
         			.queryParam(ConstantValues.FIELDS, fields)
         			.queryParam(ConstantValues.hasReservedTimes, true)
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return projectList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}

}
