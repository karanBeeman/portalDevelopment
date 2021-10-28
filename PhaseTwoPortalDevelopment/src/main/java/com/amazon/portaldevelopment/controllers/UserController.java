package com.amazon.portaldevelopment.controllers;

import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.portaldevelopment.constants.ConstantValues;
import com.amazon.portaldevelopment.services.User;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class UserController {
	
	@Autowired
	User user;
	
	@RequestMapping(value = "userCount/{userEmail}")
    public JSONObject getCount(@PathVariable String userEmail) throws JsonProcessingException, ParseException {
    	JSONObject result = user.getCount(userEmail, ConstantValues.PROJECT_USER_ID);
		return result;	
    }
	
	@RequestMapping(value = "userDetails/{userEmail}")
    public ResponseEntity<String> getUserProjectDetails(@PathVariable String userEmail) throws JsonProcessingException, ParseException {
    	String userDetails = user.getUserProjects(userEmail, ConstantValues.PROJECT_USER_ID);
		return new ResponseEntity<String>(userDetails, HttpStatus.OK);	
    }
	
	@RequestMapping(value = "pendingApprovals/{userEmail}/{date}")
    public ResponseEntity<String> getPendingApproval(@PathVariable("userEmail") String userEmail, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws JsonProcessingException, ParseException {
    	String pendingApprvls = user.getpendingApprovalsOnDate(userEmail, date, ConstantValues.PROJECT_USER_ID);
		return new ResponseEntity<String>(pendingApprvls, HttpStatus.OK);	
    }

	@RequestMapping(value = "user/proofApprovals/{userEmail}")
	public ResponseEntity<String> getProofApprovals(@PathVariable("userEmail") String userEmail) throws JsonProcessingException, ParseException, java.text.ParseException {
		String proofApprovals = user.getUserProofApproval(userEmail);
		return new ResponseEntity<String>(proofApprovals, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "utilization/{mail}")
    public ResponseEntity<String> getUtilization(@PathVariable String mail) throws ParseException, JsonProcessingException {
		String approvals = user.getUtilization(mail);
		return new ResponseEntity<String>(approvals, HttpStatus.OK);
    }
	
	@RequestMapping(value = "projectStatus/{mail}")
    public ResponseEntity<String> getProjectStatus(@PathVariable String mail) throws ParseException, JsonProcessingException {
		String projectByStatus = user.userProjectStatus(mail, ConstantValues.PROJECT_USER_ID);
		return new ResponseEntity<String>(projectByStatus, HttpStatus.OK);
    }
	
}
