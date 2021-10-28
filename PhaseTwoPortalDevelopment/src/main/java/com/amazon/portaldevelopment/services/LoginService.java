package com.amazon.portaldevelopment.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements Login{
	
   @Autowired
   LoginServiceImpl loginService;
   
   @Autowired
   CommonService commonService;

	@Override
	public String getUserRole(String userEmail) throws ParseException {
   
		String userId = commonService.getUserWorkFrontId(userEmail);

		String userAccessObj = loginService.getUserAccessId(userId);
		JSONObject accessLevelObj = commonService.parseJsonObject(userAccessObj);
		String accessId = accessLevelObj.get("accessLevelID").toString();

		String userRoleObject = loginService.getUserRole(accessId);
		JSONObject userRoleObj = commonService.parseJsonObject(userRoleObject);
		String userRole = userRoleObj.get("name").toString();

		return userRole;
	}

}
