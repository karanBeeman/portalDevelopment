package com.amazon.portaldevelopment.controllers;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.entity.ManagerEntity;
import com.amazon.portaldevelopment.services.Manager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class ManagerController {
	
	@Autowired
    Manager managerService;
	
	@RequestMapping(value = "/managerCount/{mail}")
    public JSONObject getTotalCount(@PathVariable String mail) throws ParseException, JsonProcessingException {
    	JSONObject result = managerService.getTotalCount(mail, ConstantValues.OWNER_ID);
    	return result;
    }
	
	@RequestMapping(value = "/managerProjects/{mail}")
    public String getProjectList(@PathVariable String mail) throws ParseException, JsonMappingException, JsonProcessingException {
    	String projectList = managerService.getProjectList(mail);
    	return projectList;
    }
	
	@RequestMapping(value = "/approvalByDate/{mail}/{date}") 
    public ResponseEntity<String> getPendingApprovalOnDate(@PathVariable String mail, @PathVariable String date) throws ParseException, JsonProcessingException {
    	String approvals = managerService.getApprovalByDate(mail, date, ConstantValues.OWNER_ID);
    	return new ResponseEntity<String>(approvals, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/proofApprovals/{mail}") 
    public String getProofApprovals(@PathVariable String mail) throws ParseException, JsonProcessingException {
    	String approvals = managerService.getProofApproval(mail);
    	return approvals;
    }
	
	@RequestMapping(value = "/projectStatus/count/{mail}") 
    public JSONObject getCountOfProjectByStatus(@PathVariable String mail) throws ParseException, JsonProcessingException {
    	JSONObject approvals = managerService.getProjectCountByStatus(mail);
		return approvals;
    }
	
	@RequestMapping(value = "/poStatus/count/{mail}") 
    public JSONObject getCountOfPoStatus(@PathVariable String mail) throws ParseException, JsonProcessingException {
    	JSONObject approvals = managerService.getPoStatusCount(mail);
		return approvals;
    }
	
	@RequestMapping(value = "/unavailable/{date}") 
    public String getUnAvailable(@PathVariable String date) throws ParseException, JsonProcessingException {
    	String approvals = managerService.getUnavailable(date);
		return approvals;
    }

}