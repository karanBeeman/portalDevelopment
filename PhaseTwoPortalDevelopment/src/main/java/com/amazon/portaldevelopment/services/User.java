package com.amazon.portaldevelopment.services;

import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface User {
	
	JSONObject getCount(String userEmail, String id) throws ParseException, JsonProcessingException;

	String getUserProjects(String userEmail, String id) throws ParseException, JsonMappingException, JsonProcessingException;

	String getpendingApprovalsOnDate(String userEmail, LocalDate date, String id) throws JsonProcessingException, ParseException;

	String getUserProofApproval(String userEmail) throws ParseException;

	String getUtilization(String mail) throws ParseException, JsonProcessingException;

	String userProjectStatus(String mail, String projectUserId) throws ParseException, JsonProcessingException;
}