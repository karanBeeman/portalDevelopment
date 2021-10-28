package com.amazon.portaldevelopment.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.models.WFIssuesByDate;
import com.amazon.portaldevelopment.models.WFIssuesByDateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommonService {
	
	@Autowired
	CommonServiceImpl commonServiceImpl;
	
	public String getUserWorkFrontId(String userEmail) throws ParseException {
		String userId = null;
		
		String response = commonServiceImpl.getUserId(userEmail);
		JSONArray dataArray = parseJsonArray(response);
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject userObjectId = (JSONObject) dataArray.get(i);
			userId = userObjectId.get("ID").toString();
		}
		return userId;
	}
	
	public JSONObject getTotalCount(String mail, String id) throws ParseException, JsonProcessingException {
		JSONParser parser = new JSONParser();
		HashMap<String, Integer> map = new HashMap<>();
		try {
			int projectCount = getProjectCount(mail, id);
			int delayedCount = getProjectDelayedCount(mail, id);
			int poRequest = getCountofPoRequest(mail, id);
			map.put("projectCount", projectCount);
			map.put("delayedCount", delayedCount);
			map.put("poRequest", poRequest);
		} catch (JsonProcessingException | ParseException | java.text.ParseException e) {
			e.printStackTrace();
		}
		String output = new ObjectMapper().writeValueAsString(map);
        JSONObject result = (JSONObject) parser.parse(output);
		return result;
	}
	
	private int getProjectCount(String mail, String id) throws ParseException, JsonProcessingException {
		String userId = getUserWorkFrontId(mail);
    	String response = commonServiceImpl.getProjectCountImpl(userId, id);
        JSONObject dataRootNode = parseJsonObject(response);
        Long result = (Long) dataRootNode.get(ConstantValues.COUNT);
        int count = result.intValue();
		return count;
    }
	
	private int getProjectDelayedCount(String mail, String id) throws ParseException, java.text.ParseException, JsonProcessingException {
		String userId = getUserWorkFrontId(mail);
		String projectList = commonServiceImpl.getProjectListImpl(id, userId, ConstantValues.CONDITION);
		JSONArray dataRootNode = parseJsonArray(projectList);
		int count = 0;
		for(int i = 0; i <dataRootNode.size(); i++) {
			JSONObject jsonObject = (JSONObject) dataRootNode.get(i);
			String progressStatus = (String) jsonObject.get(ConstantValues.CONDITION);
			if(progressStatus.equalsIgnoreCase(ConstantValues.atRisk) || progressStatus.equalsIgnoreCase(ConstantValues.inTrouble)) {
				count++;
			}
		}
		return count;
	}
	
	private int getCountofPoRequest(String mail, String id) throws ParseException, JsonProcessingException {
		String userId = getUserWorkFrontId(mail);
		int count = 0;
		String allProjectList = commonServiceImpl.getProjectListImpl(id, userId,ConstantValues.ID);
        JSONArray rootNodeData = parseJsonArray(allProjectList);
        for (int i = 0; i < rootNodeData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) rootNodeData.get(i);
        	String projectId = (String) jsonObject.get(ConstantValues.ID);
        	String issueList = commonServiceImpl.getIssueCount(projectId);
            JSONObject dataRootNode = parseJsonObject(issueList);
            Long result = (Long) dataRootNode.get(ConstantValues.COUNT);
            count += result.intValue();
        }
		return count;
	}
	
	public String getProjectList(String userEmail, String param) throws JsonProcessingException, ParseException {
		Map<String, List<WFIssuesByDate>> map = new HashMap<>();
		List<WFIssuesByDate> managerList = new ArrayList<>();
		List<WFIssuesByDate> issuesList = new ArrayList<>();
		List<String> arrayList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		String fields = "ID,name,ownerID,percentComplete,plannedCompletionDate,plannedStartDate,status,description";
		String userId = getUserWorkFrontId(userEmail);
		String response = commonServiceImpl.getProjectListImpl(param, userId, fields);
        JSONArray projectData = parseJsonArray(response);
        for(int i = 0; i < projectData.size(); i++) {
        	JSONObject jsonObj = (JSONObject) projectData.get(i);
        	String status = jsonObj.get(ConstantValues.STATUS).toString();
        	if(status.equalsIgnoreCase("WTN") || status.equalsIgnoreCase("CUR") || status.equalsIgnoreCase("RSF")) {
        		arrayList.add(jsonObj.toString());
        	}
        }
        issuesList = Arrays.asList(mapper.readValue(arrayList.toString(), WFIssuesByDate[].class));
        issuesList.sort(Comparator.comparing(WFIssuesByDate::getPlannedCompletionDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        int value;
        if(issuesList.size() > 5 ) {
        	value = 5;
        } else {
        	value = issuesList.size();
        }
        for(int i=0; i < value; i++) {
        	WFIssuesByDate obj = issuesList.get(i);
        	String result = commonServiceImpl.getManagerNameDetails(obj.getOwnerID());
        	JSONObject managerObj = parseJsonObject(result);
        	obj.setManagerName(managerObj.get(ConstantValues.NAME).toString());
        	obj.setStatus(renameStatusValue(obj.getStatus().toString()));
        	obj.setPlannedCompletionDate(removeTimeStamp(obj.getPlannedCompletionDate().toString()));
        	obj.setPlannedStartDate(removeTimeStamp(obj.getPlannedCompletionDate().toString()));
        	managerList.add(obj);
        }
        map.put(ConstantValues.DATA, managerList);
        String output = new ObjectMapper().writeValueAsString(map);
        return output;
	}
	
	private String removeTimeStamp(String date) {
		String dateResult = date.substring(0, 10);
		return dateResult;
	}

	private String renameStatusValue(String status) {
		String result = "";
		if(status.equalsIgnoreCase(ConstantValues.CUR)) {
			result = "In Flight";
		} else if(status.equalsIgnoreCase(ConstantValues.RSF)) {
		   result = "Awaiting Kick off";
		} else if(status.equalsIgnoreCase(ConstantValues.WTN)) {
			result = "Waiting";
		}
		return result;
	}

	public String approvalsByDate(String userEmail, String date, String params) throws JsonProcessingException, ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate selectedDate = LocalDate.parse(date, formatter);
		List<WFIssuesByDateResponse> nuxeo = nuxeoApprovalByDate(userEmail, selectedDate);
		List<WFIssuesByDateResponse> workFront = wfApprovalByDate(userEmail, date.toString(), params);
		List<List<WFIssuesByDateResponse>> result = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		Map <String, List<List<WFIssuesByDateResponse>>> map = new HashMap<>();
		Map<String, JSONObject> mapping = new HashMap<>();
		jsonObj.put("nuxeo", nuxeo);
		jsonObj.put("workFront", workFront);
		mapping.put("data", jsonObj);
		result.add(nuxeo);
		result.add(workFront);
		map.put("data", result);
//		map.put("nuxeo", nuxeo);
//		map.put("workFront", workFront);
		String output = new ObjectMapper().writeValueAsString(map);
		System.out.println(output);
		return output;
	}
	
	private List<WFIssuesByDateResponse> nuxeoApprovalByDate(String userEmail, LocalDate date) throws ParseException, JsonProcessingException {
		JSONParser parser = new JSONParser();
		List<WFIssuesByDate> nxList = new ArrayList<>();
		List<WFIssuesByDateResponse> nxResponseList = new ArrayList<>();
		String userId = trimUserEmail(userEmail);
		String response = commonServiceImpl.getAdminPendingApprovals(userId);
		JSONArray pendingApproval = parseResponseValue(response);
		for (int j = 0; j < pendingApproval.size(); j++) {
			WFIssuesByDateResponse nxResponse = new WFIssuesByDateResponse();
			JSONObject pendingAprvl = (JSONObject) pendingApproval.get(j);
			JSONArray jsonArr = (JSONArray) pendingAprvl.get(ConstantValues.targetDocumentIds);
			JSONObject id = (JSONObject) jsonArr.get(0);
			String targetDocId = (String) id.get("id");
			String assetDetails = commonServiceImpl.getAssetDetails(targetDocId);
			JSONObject assetJson = (JSONObject) parser.parse(assetDetails);
			String assetTile = (String) assetJson.get(ConstantValues.title);
			String localDate = (String) pendingAprvl.get(ConstantValues.dueDate);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
			LocalDate dueDate = LocalDate.parse(removeTimeStamp(localDate), formatter);
			if (dueDate.equals(date) || dueDate.isBefore(date)) {
				WFIssuesByDate nx = new WFIssuesByDate();
				nx.setAssetTitle(assetTile);
				nx.setDueDate(dueDate.toString());
				nx.setTaskName(pendingAprvl.get(ConstantValues.NAME).toString());
				nxList.add(nx);
				nxResponse.setIssuesByDate(nxList);
				nxResponseList.add(nxResponse);
			}
		}
		return nxResponseList;
	}
	
	public List<WFIssuesByDateResponse> wfApprovalByDate(String mail, String selectedDate, String param) throws ParseException, JsonProcessingException {
		String id = getUserWorkFrontId(mail);
		String fields= "ID,name";
		List<WFIssuesByDateResponse> wfResponseList = new ArrayList<>();
		String allProjectList = commonServiceImpl.getProjectListImpl(param, id, fields);
        JSONArray projectData = parseJsonArray(allProjectList);
        for (int i = 0; i < projectData.size(); i++) {
        	JSONObject jsonObject = (JSONObject) projectData.get(i);
        	String projectId = (String) jsonObject.get(ConstantValues.ID);
        	String issues = commonServiceImpl.getIssueDetails(projectId);
        	JSONArray issueData = parseJsonArray(issues);
        	if(!getIssueByDate(issueData, selectedDate).isEmpty()) {
        		WFIssuesByDateResponse wfResponse = new WFIssuesByDateResponse();
        		wfResponse.setIssuesByDate(getIssueByDate(issueData, selectedDate));
        		wfResponseList.add(wfResponse);
        	}
        }
     	return wfResponseList;
	}
	
	private List<WFIssuesByDate> getIssueByDate(JSONArray issueData, String date) {
		List<WFIssuesByDate> issuesList = new ArrayList<>();
		WFIssuesByDate wfIssues = null;
		for(int i = 0; i <issueData.size(); i++) {
			JSONObject data = (JSONObject) issueData.get(i);
			String issueDate = (String) data.get(ConstantValues.PLANNED_COMPLETION_DATE);
			date = date.replace("-", "/");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate selectedDate = LocalDate.parse(date, formatter);
			Boolean flag = false;
			String status = data.get(ConstantValues.STATUS).toString();
			String plannedCompletion = "";
			String plannedStart = "";
			if(data.get(ConstantValues.PLANNED_COMPLETION_DATE) !=null) {
				plannedCompletion = removeTimeStamp(data.get(ConstantValues.PLANNED_COMPLETION_DATE).toString());
			} else if(data.get(ConstantValues.PLANNED_START_DATE) != null) {
				plannedStart = removeTimeStamp(data.get(ConstantValues.PLANNED_START_DATE).toString());
			}
			if(issueDate != null) {
				flag = checkDate(issueDate, selectedDate);
			}
			if((status.equalsIgnoreCase(ConstantValues.NEW) || status.equalsIgnoreCase(ConstantValues.inProgress) || status.equalsIgnoreCase(ConstantValues.awaitingFeedback)) && flag == true) {
				Double doubleValue = (Double) data.get(ConstantValues.percentComplete);
				wfIssues = new WFIssuesByDate(data.get(ConstantValues.objCode).toString(), data.get(ConstantValues.NAME).toString(), "", plannedStart
						, plannedCompletion, data.get(ConstantValues.ID).toString(), doubleValue
								, data.get(ConstantValues.STATUS).toString());		
				issuesList.add(wfIssues);
        	}
		}
		return issuesList;
	}
	
	public String trimUserEmail(String userEmail) {
		String trim = userEmail.substring(0, userEmail.indexOf("@"));
		return trim;
	}

	private Boolean checkDate(String issueDate, LocalDate selectedDate) {
		String date = issueDate.substring(0, 10).replace("-", "/");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate formattedDate = LocalDate.parse(date, formatter);
		if(formattedDate.isEqual(selectedDate) || formattedDate.isBefore(selectedDate)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public JSONArray getProjectListByStatus(String mail, String id) throws ParseException {
		String userId = getUserWorkFrontId(mail);	
		String allApprovalList = commonServiceImpl.getProjectListImpl(id, userId, ConstantValues.CONDITION);
        JSONArray rootNodeData = parseJsonArray(allApprovalList);
		return rootNodeData;
	}
  
	public JSONObject parseJsonObject(String data) throws ParseException {
		 JSONParser parser = new JSONParser();
		 JSONObject projObj = (JSONObject) parser.parse(data);
        JSONObject obj = (JSONObject) projObj.get(ConstantValues.DATA);
		return obj;
	}
	
	public JSONArray parseJsonArray(String data) throws ParseException {
		 JSONParser parser = new JSONParser();
		 JSONObject jsonObject = (JSONObject) parser.parse(data);
	     JSONArray jsonArray = (JSONArray) jsonObject.get(ConstantValues.DATA);
		return jsonArray;
	}
	
	public JSONArray parseResponseValue(String response) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = (JSONArray) jsonObj.get(ConstantValues.entries);
		return jsonArray;
	}
	
	public int assetStatusParse(String asset) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonValue = (JSONObject) parser.parse(asset);
		Long longValue = (Long) jsonValue.get(ConstantValues.resultsCount);
		int assetCount = longValue.intValue();
		return assetCount;
	}
}