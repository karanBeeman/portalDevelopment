package com.amazon.portaldevelopment.services;

import java.time.LocalDate;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Administrator {

	public String getApprovalPendingCount(String userEmail) throws JsonProcessingException;

	public String getWFLicenseCount() throws JsonProcessingException, ParseException;

	public String getPendingApprovalForSelectedDate(String userEmail, LocalDate date) throws JsonProcessingException, ParseException;

	public String getAssetStatus() throws ParseException, JsonProcessingException;

	public String getNuxeoStorageSize() throws ParseException, JsonProcessingException;

	public String getAssetTypeCount() throws ParseException, JsonProcessingException;

	public String getNuxeoLicenseCount() throws JsonProcessingException;

	public String getWorkFrontStorage() throws ParseException, JsonProcessingException;

}
