package com.amazon.portaldevelopment.controllers;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.portaldevelopment.services.Login;

@RestController
public class LoginController {
	
	@Autowired
	Login login;
	
	@GetMapping("/login/{userEmail}") 
	public ResponseEntity<String> getUserRole(@PathVariable String userEmail) throws ParseException {
    String userRole = login.getUserRole(userEmail);
		return new ResponseEntity<String>(userRole, HttpStatus.OK);
	}

}
