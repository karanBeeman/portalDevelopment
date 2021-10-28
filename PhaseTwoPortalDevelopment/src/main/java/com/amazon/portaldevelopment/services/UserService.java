package com.amazon.portaldevelopment.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.portaldevelopment.constants.ConstantValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService implements User {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CommonServiceImpl commonServiceImpl;
	
	@Override
	public JSONObject getCount(String userEmail, String id) throws JsonProcessingException, ParseException {
		return commonService.getTotalCount(userEmail, id);
	}

	@Override
	public String getUserProjects(String userEmail, String param) throws ParseException, JsonMappingException, JsonProcessingException {
		return commonService.getProjectList(userEmail, param);
	}

	@Override
	public String getpendingApprovalsOnDate(String userEmail, LocalDate date, String id) throws JsonProcessingException, ParseException {
		return commonService.approvalsByDate(userEmail, date.toString(), id);
	}

	@Override
	public String getUserProofApproval(String userEmail) throws ParseException {
		String id = commonService.getUserWorkFrontId(userEmail);
		List<Object> result = new ArrayList<>();
		String approvalJson = commonServiceImpl.getApprovalList(id);
        JSONArray approvalArray = commonService.parseJsonArray(approvalJson);
        for(int i = 0; i < approvalArray.size(); i++) {
        	JSONObject jsonObject = (JSONObject) approvalArray.get(i);
        	String approverDecision = (String) jsonObject.get(ConstantValues.approverDecision);
        	if(approverDecision.equalsIgnoreCase(ConstantValues.ChangesRequired) || approverDecision.equalsIgnoreCase(ConstantValues.NotRelevant)) {
        		result.add(jsonObject);
        	}
        }
        String resultJSON = JSONArray.toJSONString(result);
		return resultJSON;
	}

	@Override
	public String getUtilization(String mail) throws ParseException, JsonProcessingException {
		   String id = commonService.getUserWorkFrontId(mail);
	        Map<String, Double> map = new HashMap<>();
	        String fields = "status,projectID,work";
	        String response = commonServiceImpl.getUtilizationImp(id, fields);
	        JSONArray jsonArray = commonService.parseJsonArray(response);
	        for(int i = 0; i<jsonArray.size(); i++) {
	            Map<String, Double> tempMap = new HashMap<>();
	            JSONObject utilizationDetails = (JSONObject) jsonArray.get(i);
	            Double work = (Double) utilizationDetails.get(ConstantValues.WORK);
	            String projectID = (String) utilizationDetails.get(ConstantValues.projectID);
	            String projectResponse = userServiceImpl.getProjectNameObj(projectID);
	            JSONObject projObj = commonService.parseJsonObject(projectResponse);
	            String projName = projObj.get(ConstantValues.NAME).toString();
	            String status = (String) utilizationDetails.get(ConstantValues.STATUS);
	            if(status.equalsIgnoreCase(ConstantValues.inProgress) || status.equalsIgnoreCase(ConstantValues.NEW))
	            if(map.get(projName) == null) {
	                tempMap.put(projName, work);
	                map.putAll(tempMap);
	            }
	            else {
	                Double prevWork = map.get(projName);
	                Double currentWork = prevWork + work;
	                map.replace(projName, currentWork);
	            }
	        }
	        String output = new ObjectMapper().writeValueAsString(map);
		return output;
	}

	@Override
	public String userProjectStatus(String mail, String projectUserId) throws ParseException, JsonProcessingException {
		int onTarget = 0, atRisk = 0, inTrouble = 0;
		HashMap<String, Integer> map = new HashMap<>();
		JSONArray rootNodeData = commonService.getProjectListByStatus(mail, projectUserId);
        for (int i = 0; i < rootNodeData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) rootNodeData.get(i);
        	String projectId = (String) jsonObject.get(ConstantValues.ID);
        	String projectDataList = commonServiceImpl.getProjectDetails(projectId, ConstantValues.CONDITION);
        	JSONObject projectData = commonService.parseJsonObject(projectDataList);
        	String projectCondition = (String) projectData.get(ConstantValues.CONDITION);
        	if(projectCondition.equalsIgnoreCase(ConstantValues.inTrouble)) {
        		inTrouble++;
        	} else if(projectCondition.equalsIgnoreCase(ConstantValues.atRisk)) {
        		atRisk++;
        	} else if(projectCondition.equalsIgnoreCase(ConstantValues.onTarget)) {
        		onTarget++;
        	}
        }
        map.put("onTarget", onTarget);
        map.put("atRisk", atRisk);
        map.put("inTrouble", inTrouble);
        String output = new ObjectMapper().writeValueAsString(map);
		return output;
	}
}
