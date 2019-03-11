package com.security.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.oauth2.dao.IOAuth2Dao;
import com.security.oauth2.model.UserInfo;

@RestController
public class OAuth2Controller {
	
	@Autowired
	IOAuth2Dao iOAuth2Dao;

	@RequestMapping("/hello")
	String hello() {
		return "SPRING OAuth2 and JWT";
	}
	
	@RequestMapping(value="/api/oauth2/users", method=RequestMethod.GET, produces="application/json")
	public List<UserInfo>getUsers(){		
		return iOAuth2Dao.getUsers();		
	}	
}
