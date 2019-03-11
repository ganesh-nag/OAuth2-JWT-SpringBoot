package com.security.oauth2.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.oauth2.dao.IOAuth2Dao;
import com.security.oauth2.model.UserInfo;


@Service
public class UserDetailDBService implements UserDetailsService{
	
	@Autowired
	IOAuth2Dao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {		
		UserInfo userInfo = dao.getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		UserDetails userDetails = (UserDetails)new User(userInfo.getuName(), 
				userInfo.getPassword(), Arrays.asList(authority));		
		return userDetails;
	}

}
