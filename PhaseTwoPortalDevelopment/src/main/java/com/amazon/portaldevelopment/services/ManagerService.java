package com.amazon.portaldevelopment.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.Repository.ManagerRepository;
import com.amazon.portaldevelopment.constants.ConstantValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ManagerService implements Manager{
	
	@Autowired
	ManagerServiceImpl managerServiceImpl;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CommonServiceImpl commonServiceImpl;
	
	@Autowired
	ManagerRepository managerRepository;


	public JSONObject getTotalCount(String userEmail, String id) throws JsonProcessingException, ParseException {
		return commonService.getTotalCount(userEmail, id);
	}

	public String getProjectList(String mail) throws ParseException, JsonMappingException, JsonProcessingException {
		 return commonService.getProjectList(mail, ConstantValues.OWNER_ID);    
	}

	@Override
	public String getApprovalByDate(String mail, String selectedDate, String param) throws ParseException, JsonProcessingException {
	    return commonService.approvalsByDate(mail, selectedDate, param);
	}
	
	@Override
	public String getProofApproval(String mail) throws ParseException {
		String id = commonService.getUserWorkFrontId(mail);
		List<Object> result = new ArrayList<>();
		String approvalList = commonServiceImpl.getApprovalList(id);
        JSONArray approvalArray = commonService.parseJsonArray(approvalList);
        for(int i = 0; i < approvalArray.size(); i++) {
        	JSONObject jsonObject = (JSONObject) approvalArray.get(i);
        	String approverDecision = (String) jsonObject.get(ConstantValues.approverDecision);
        	if(approverDecision.equalsIgnoreCase(ConstantValues.pending)) {
        		result.add(jsonObject);
        	}
        }
        String resultJSON = JSONArray.toJSONString(result);
		return resultJSON;
	}
	
	@Override
	public JSONObject getProjectCountByStatus(String mail) throws ParseException, JsonProcessingException {
		JSONParser parser = new JSONParser();
		int onTarget = 0, atRisk = 0, inTrouble = 0;
		HashMap<String, Integer> map = new HashMap<>();
		JSONArray rootNodeData = commonService.getProjectListByStatus(mail, ConstantValues.OWNER_ID);
        for (int i = 0; i < rootNodeData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) rootNodeData.get(i);
        	String projectId = (String) jsonObject.get(ConstantValues.ID);
        	String projectDataList = commonServiceImpl.getProjectDetails(projectId, ConstantValues.CONDITION);
        	JSONObject projectData = commonService.parseJsonObject(projectDataList);
        	String projectCondition = (String) projectData.get(ConstantValues.CONDITION);
        	if(projectCondition.equalsIgnoreCase(ConstantValues.inTrouble)) {
        		inTrouble++;
        	}
        	if(projectCondition.equalsIgnoreCase(ConstantValues.atRisk)) {
        		atRisk++;
        	}
        	if(projectCondition.equalsIgnoreCase(ConstantValues.onTarget)) {
        		onTarget++;
        	}
        }
        map.put("onTarget", onTarget);
        map.put("atRisk", atRisk);
        map.put("inTrouble", inTrouble);
        String output = new ObjectMapper().writeValueAsString(map);
        JSONObject result = (JSONObject) parser.parse(output);
		return result;
	}
	
	@Override
	public JSONObject getPoStatusCount(String mail) throws ParseException, JsonProcessingException {
		String id = commonService.getUserWorkFrontId(mail);
		int inProgress = 0, New = 0;
		Map<String, Integer> map = new HashMap<>();
		String projectList = commonServiceImpl.getProjectListImpl(ConstantValues.OWNER_ID, id, ConstantValues.ID);
		JSONParser parser = new JSONParser();
        JSONArray rootNodeData = commonService.parseJsonArray(projectList);
        for (int i = 0; i < rootNodeData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) rootNodeData.get(i);
        	String projectId = (String) jsonObject.get(ConstantValues.ID);
        	String issueDataList = commonServiceImpl.getIssueDetails(projectId);
        	JSONArray issueData = commonService.parseJsonArray(issueDataList);
        	for(int j = 0; j < issueData.size(); j++) {
        		JSONObject issueObject = (JSONObject) issueData.get(j);
	        	String issueStatus = (String) issueObject.get(ConstantValues.STATUS);
	        	if(issueStatus.equalsIgnoreCase(ConstantValues.NEW)) {
	        		New++;
	        	}
	        	if(issueStatus.equalsIgnoreCase(ConstantValues.inProgress)) {
	        		inProgress++;
	        	}
        	}
        }
        map.put("inProgress", inProgress);
        map.put("New", New);
        String output = new ObjectMapper().writeValueAsString(map);
        JSONObject result = (JSONObject) parser.parse(output);
		return result;
	}
	
	@Override
	public String getUnavailable(String selectedDate) throws ParseException, JsonProcessingException {
		String fields = "ID,reservedTimes:*";
		String startDate, endDate, userName, userID = null;
		Boolean flag = false;
		List <HashMap<String, String> > map = new ArrayList<>();
		String response = managerServiceImpl.getTimeOffDetails(fields);
        JSONArray rootNodeData = commonService.parseJsonArray(response);
        for (int i = 0; i < rootNodeData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) rootNodeData.get(i);
        	JSONArray reservedTimes = (JSONArray) jsonObject.get(ConstantValues.reservedTimes);
        	for(int j = 0; j < reservedTimes.size(); j++) {
        		JSONObject reservedTimesData = (JSONObject) reservedTimes.get(j);
        		startDate = (String) reservedTimesData.get(ConstantValues.startDate);
        		endDate = (String) reservedTimesData.get(ConstantValues.endDate);
        		flag = timeOffCheck(startDate, endDate, selectedDate);
        		if(flag) {
        			userID = (String) jsonObject.get(ConstantValues.ID);
        			userName = (String) jsonObject.get(ConstantValues.NAME);
        			HashMap<String, String> tempMap = new HashMap<String, String>();
        			tempMap.put(ConstantValues.ID, userID);
        			tempMap.put(ConstantValues.NAME, userName);
        			if(!map.contains(tempMap)) {
        				map.add(tempMap);
        			}
        		}
        	}
        }
        String result = new ObjectMapper().writeValueAsString(map);
		return result;
	}
	
	private Boolean timeOffCheck(String startDate, String endDate, String selectedDate) {
		startDate = startDate.substring(0, 10).replace("-", "/");
		endDate = endDate.substring(0, 10).replace("-", "/");
		selectedDate = selectedDate.substring(0, 10).replace("-", "/");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startingDate = LocalDate.parse(startDate, formatter);
		LocalDate endingDate = LocalDate.parse(endDate, formatter);
		LocalDate date = LocalDate.parse(selectedDate, formatter);
		if(startingDate.equals(date) || endingDate.equals(date) || (date.isAfter(startingDate) && date.isBefore(endingDate))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
