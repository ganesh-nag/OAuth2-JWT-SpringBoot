package com.security.oauth2.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.security.oauth2.model.UserInfo;



@Component
public interface IOAuth2Dao {
	public List<UserInfo>getUsers();
	public UserInfo getUserInfo(String uname);
}
