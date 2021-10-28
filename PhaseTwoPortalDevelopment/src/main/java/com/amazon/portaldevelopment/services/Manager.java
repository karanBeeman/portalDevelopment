package com.amazon.portaldevelopment.services;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.amazon.portaldevelopment.entity.ManagerEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Manager {
	
	public JSONObject getTotalCount(String mail, String ownerId) throws ParseException, JsonProcessingException;

	public String getProjectList(String mail) throws ParseException, JsonMappingException, JsonProcessingException;

	public String getApprovalByDate(String mail, String date, String id) throws ParseException, JsonProcessingException;

	public JSONObject getProjectCountByStatus(String mail) throws ParseException, JsonProcessingException;

	public JSONObject getPoStatusCount(String mail) throws ParseException, JsonProcessingException;

	public String getUnavailable(String date) throws ParseException, JsonProcessingException;

	public String getProofApproval(String mail) throws ParseException;

}
