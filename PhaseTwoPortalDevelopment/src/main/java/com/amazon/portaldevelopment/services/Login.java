package com.amazon.portaldevelopment.services;

import org.json.simple.parser.ParseException;

public interface Login {

	public String getUserRole(String userEmail) throws ParseException;
	
}
