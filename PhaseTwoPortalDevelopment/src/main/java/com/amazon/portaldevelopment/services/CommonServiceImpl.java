package com.amazon.portaldevelopment.services;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;
import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.exceptions.ServiceException;

@Service
public class CommonServiceImpl {
	
	@Autowired
	WebClient.Builder webClient;

	@Autowired
	ConfigurationPropertiesClass config;
	
	public String getUserId(String userMail) {
		try {
			WebClient client = webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
			String response = client.get().uri(uriBuilder -> uriBuilder.path("user/search")
					.queryParam("apiKey", config.getWorkFrontApiKey())
					.queryParam("emailAddr", userMail).build())
					.retrieve().bodyToMono(String.class)
					.block();
			return response;
		} catch (WebClientResponseException e) {
			throw new ServiceException(e.getMessage(), e.getRawStatusCode());
		}
	}
	
	public String getProjectListImpl(String param, String id, String fields ) throws ParseException {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String projectList = client.get().uri(uriBuilder -> uriBuilder
         			.path("proj/search")
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.queryParam(param, id)
         			.queryParam(ConstantValues.FIELDS, fields)
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return projectList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getProjectDetails(String id, String fields) {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String projectList = client.get().uri(uriBuilder -> uriBuilder
         			.path("proj/")
         			.path(id)
         			.path("/search")
         			.queryParam(ConstantValues.FIELDS, fields)
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return projectList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getApprovalList(String id) {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String projectList = client.get().uri(uriBuilder -> uriBuilder
         			.path("PRFAPL/search")
         			.queryParam(ConstantValues.approverID, id)
         			.queryParam(ConstantValues.FIELDS, "*")
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return projectList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getManagerNameDetails(String ownerID) {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String managerDetailList = client.get().uri(uriBuilder -> uriBuilder
         			.path("user/" + ownerID)
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return managerDetailList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getProjectCountImpl(String id, String param) throws ParseException {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String userResponse = client.get().uri(uriBuilder -> uriBuilder
         			.path("proj/count/search")
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.queryParam(param, id)
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return userResponse;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getIssueDetails(String projectId) {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String issueList = client.get().uri(uriBuilder -> uriBuilder
         			.path("issue/search")
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.queryParam(ConstantValues.projectID, projectId)
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return issueList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}
	
	public String getIssueCount(String projectId) {
		try{
         	WebClient client =	webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
         	String projectList = client.get().uri(uriBuilder -> uriBuilder
         			.path("issue/count/search")
         			.queryParam(ConstantValues.projectID, projectId)
         			.queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
         			.build())
         	        .retrieve()
         	        .bodyToMono(String.class)
         	        .block();
         	return projectList;
         }catch (WebClientResponseException e){
             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
         }
	}

	public String getUtilizationImp(String id, String fields) {
		  try{
	            WebClient client =  webClient.baseUrl(config.getWorkFrontBaseUrl()).build();
	            String projectList = client.get().uri(uriBuilder -> uriBuilder
	                    .path("work/search")
	                    .queryParam("assignedToID", id)
	                    .queryParam(ConstantValues.FIELDS, fields)
	                    .queryParam(ConstantValues.API_KEY, config.getWorkFrontApiKey())
	                    .build())
	                    .retrieve()
	                    .bodyToMono(String.class)
	                    .block();
	            return projectList;
	         }catch (WebClientResponseException e){
	             throw new ServiceException(e.getMessage(), e.getRawStatusCode());
	         }
	}
	
	public String getAdminPendingApprovals(String userId) {
		  try {
        	WebClient client =	webClient.baseUrl(config.getNuxeoBaseUrl()).build();
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("task")
        			.queryParam(ConstantValues.userId, userId)
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, config.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response;
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}
	
	public String getAssetDetails(String targetDocId) {
		try {
        	WebClient client =	webClient.baseUrl(config.getNuxeoBaseUrl()).build();
        	
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("id/" + targetDocId)
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, config.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}

}
