package com.amazon.portaldevelopment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class ConfigurationPropertiesClass {
	
	private String nuxeoApiKey;

	private String workFrontApiKey;
	
	private String nuxeoBaseUrl;
	
	private String workFrontBaseUrl;
	
	private String workFrontInternalUrl;
	
	private int totalNuxeoLicense;
	
	private Double totalNuxeoStorage;

	public String getWorkFrontInternalUrl() {
		return workFrontInternalUrl;
	}

	public void setWorkFrontInternalUrl(String workFrontInternalUrl) {
		this.workFrontInternalUrl = workFrontInternalUrl;
	}
	
	public String getNuxeoApiKey() {
		return nuxeoApiKey;
	}

	public void setNuxeoApiKey(String nuxeoApiKey) {
		this.nuxeoApiKey = nuxeoApiKey;
	}

	public String getWorkFrontApiKey() {
		return workFrontApiKey;
	}

	public void setWorkFrontApiKey(String workFrontApiKey) {
		this.workFrontApiKey = workFrontApiKey;
	}

	public String getNuxeoBaseUrl() {
		return nuxeoBaseUrl;
	}

	public void setNuxeoBaseUrl(String nuxeoBaseUrl) {
		this.nuxeoBaseUrl = nuxeoBaseUrl;
	}
	
	public String getWorkFrontBaseUrl() {
		return workFrontBaseUrl;
	}

	public void setWorkFrontBaseUrl(String workFrontBaseUrl) {
		this.workFrontBaseUrl = workFrontBaseUrl;
	}

	public int getTotalNuxeoLicense() {
		return totalNuxeoLicense;
	}

	public void setTotalNuxeoLicense(int totalNuxeoLicense) {
		this.totalNuxeoLicense = totalNuxeoLicense;
	}
	
	public Double getTotalNuxeoStorage() {
		return totalNuxeoStorage;
	}

	public void setTotalNuxeoStorage(Double totalNuxeoStorage) {
		this.totalNuxeoStorage = totalNuxeoStorage;
	}

	@Override
	public String toString() {
		return "ConfigurationPropertiesClass [nuxeoApiKey=" + nuxeoApiKey + ", workFrontApiKey=" + workFrontApiKey
				+ ", nuxeoBaseUrl=" + nuxeoBaseUrl + ", workFrontBaseUrl=" + workFrontBaseUrl
				+ ", workFrontInternalUrl=" + workFrontInternalUrl + ", totalNuxeoLicense=" + totalNuxeoLicense
				+ ", totalNuxeoStorage=" + totalNuxeoStorage + "]";
	}

}
