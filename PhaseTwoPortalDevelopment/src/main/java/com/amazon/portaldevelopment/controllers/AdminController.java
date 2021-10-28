package com.amazon.portaldevelopment.controllers;

import com.amazon.portaldevelopment.services.Administrator;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    Administrator adminService;
    
    @RequestMapping(value = "nuxeo/approvalPending/{userEmail}")
    public ResponseEntity<String> totalCountOfApprovalPending(@PathVariable String userEmail) throws JsonProcessingException {
    	String pendingApproval = adminService.getApprovalPendingCount(userEmail);
		return new ResponseEntity<String>(pendingApproval, HttpStatus.OK);	
    }
    
    @RequestMapping(value = "workFront/licenseCount")
    public ResponseEntity<String> licenseCount() throws JsonProcessingException, ParseException {
    	String licensCountJson = adminService.getWFLicenseCount();
    	return new ResponseEntity<String>(licensCountJson, HttpStatus.OK);
    }
    
    @RequestMapping(value = "nuxeo/approvalPending/{userEmail}/{date}") 
    public ResponseEntity<String> approvalPendingForSelectedDate(@PathVariable("userEmail") String userEmail, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws JsonProcessingException, ParseException {
    	String pendingApprovalOnDate = adminService.getPendingApprovalForSelectedDate(userEmail, date);
		return new ResponseEntity<String>(pendingApprovalOnDate, HttpStatus.OK);
    	
    }
    
    @RequestMapping(value = "nuxeo/assetStatus") 
    public ResponseEntity<String> assetStatus() throws ParseException, JsonProcessingException {
    		String assetJson = adminService.getAssetStatus();
    		return new ResponseEntity<String>(assetJson, HttpStatus.OK);
    }
    
    @RequestMapping(value = "nuxeo/storage")
    public ResponseEntity<String> nuxeoStorageSize() throws ParseException, JsonProcessingException {
    	String storageNx = adminService.getNuxeoStorageSize();
    	return new ResponseEntity<String>(storageNx, HttpStatus.OK);
    }
    
    @RequestMapping(value = "nuxeo/assetType/count")
    public ResponseEntity<String> assetTypeWiseCount() throws ParseException, JsonProcessingException {
    	String multipleAssetCount = adminService.getAssetTypeCount();
		return new ResponseEntity<String>(multipleAssetCount, HttpStatus.OK);
    }
    
    @RequestMapping(value = "nuxeo/LicenseCount")
    public ResponseEntity<String> nuxeoLicenseCount() throws JsonProcessingException {
    	String nuxeoLicense = adminService.getNuxeoLicenseCount();
    	return new ResponseEntity<String>(nuxeoLicense , HttpStatus.OK);
    }
    
    @RequestMapping(value = "workFront/storage")
    public ResponseEntity<String> workFrontStorageSize() throws ParseException, JsonProcessingException {
    	String workFrontStorage = adminService.getWorkFrontStorage();
    	return new ResponseEntity<String>(workFrontStorage, HttpStatus.OK);
    }
  
}