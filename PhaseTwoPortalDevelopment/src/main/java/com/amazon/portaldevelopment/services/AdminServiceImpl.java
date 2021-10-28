package com.amazon.portaldevelopment.services;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;
import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.exceptions.ServiceException;

@Service
public class AdminServiceImpl extends AdminService {
	
	@Autowired
	WebClient.Builder webClientInstance;

	@Autowired
	ConfigurationPropertiesClass configuration;

	public String getWFLicenseCount() {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getWorkFrontBaseUrl()).build();
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("group/search/")
        			.queryParam(ConstantValues.API_KEY, configuration.getWorkFrontApiKey())
        			.queryParam("licenseTypeLimit:groupID_Mod", "notnull")
        			.queryParam(ConstantValues.FIELDS, "licenseTypeLimit:usedLicenses")
        			.build())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	 return response;
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}

	public String getWfLicenseAndSize() {
		try {
        	WebClient client =	webClientInstance.baseUrl("https://xcm.preview.workfront.com/").build();
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("attask/api-internal/CUST/currentCustomer")
        			.queryParam(ConstantValues.API_KEY, configuration.getWorkFrontApiKey())
        			.queryParam(ConstantValues.FIELDS, String.join(",", "fullUsers", "teamUsers", "reviewUsers", "documentSize", "externalDocumentStorage"))
        			.build())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	 return response;
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
		
	}

	public String getAssetStatusFromWF(String Asset, String assetType) {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getNuxeoBaseUrl()).build();
        	
        	String queries = "SELECT * FROM Document WHERE ecm:mixinType != 'HiddenInNavigation' AND ecm:isProxy = 0 AND ecm:isVersion = 0 AND ecm:isTrashed = 0"
        			+ " AND " + Asset + "=" + "'" + assetType + "'";
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("search/pp/nxql_search/execute")
        			.queryParam("queryParams", queries)
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, configuration.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
		
	}
	
	public String getNuxeoStorageSize(JSONObject json) {
		try {
        	WebClient client =	webClientInstance.baseUrl("https://amazon-xcm-uat.apps.prod.nuxeo.io/nuxeo/").build();
        	
        	String response = client.post().uri("site/es/_all/doc/_search")
        	        .contentType(MediaType.APPLICATION_JSON)
        	        .accept(MediaType.APPLICATION_JSON)
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, configuration.getNuxeoApiKey())
        			.body(BodyInserters.fromValue(json))
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}

	public String getTotalNuxeoLicenseCount() {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getNuxeoBaseUrl()).build();
        	
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("user/search")
        			.queryParam("q", "*")
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, configuration.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}

	public String getAssetStatusForNonMetadata() {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getNuxeoBaseUrl()).build();
        	
        	String queries = "SELECT * FROM Document WHERE ecm:mixinType != 'HiddenInNavigation' AND ecm:isProxy = 0 AND ecm:isVersion = 0 AND ecm:isTrashed = 0 AND MA:Assettype IS NULL";
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("search/pp/nxql_search/execute")
        			.queryParam("queryParams", queries)
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, configuration.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}

	public String getUserGroupFromNuxeo(String userName) {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getNuxeoBaseUrl()).build();
        	
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("user/" + userName)
        			.build())
        			.header(ConstantValues.X_AUTHENTICATION_TOKEN, configuration.getNuxeoApiKey())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	return response; 
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}
	
	public String getWfStorageByGroup() {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getWorkFrontBaseUrl()).build();
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("docu/report")
        			.queryParam(ConstantValues.API_KEY, configuration.getWorkFrontApiKey())
        			.queryParam("owner:homeGroupID_1_GroupBy", true)
        			.queryParam("currentVersion:docSize_AggFunc", "sum")
        			.build())
        	        .retrieve()
        	        .bodyToMono(String.class)
        	        .block();
        	 return response;
        }
        catch (WebClientResponseException e){
          throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
	}
	
	public String getHomeGroupName(String homeGroupId) {
		try {
        	WebClient client =	webClientInstance.baseUrl(configuration.getWorkFrontBaseUrl()).build();
        	String response = client.get().uri(uriBuilder -> uriBuilder
        			.path("group/" + homeGroupId + "/report")
        			.queryParam(ConstantValues.API_KEY, configuration.getWorkFrontApiKey())
        			.build())
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
