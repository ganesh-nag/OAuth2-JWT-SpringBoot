package com.security.oauth2.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.security.oauth2.dao.IOAuth2Dao;
import com.security.oauth2.model.UserInfo;

@Service
public class OAuth2DaoImpl implements IOAuth2Dao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserInfo> getUsers() {

		String sql = "SELECT u.USERNAME, u.PASSWORD, u.ENABLED, a.ROLE FROM "+
			     "USERS u INNER JOIN USER_ROLES a on u.USERNAME=a.USERNAME";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<UserInfo>>() {
			 
	        public List<UserInfo> extractData(ResultSet rs) throws SQLException,
	                DataAccessException {
	        	List<UserInfo> users = new ArrayList<UserInfo>();
	            while(rs.next()) {
	                UserInfo userInfo = new UserInfo();
	                userInfo.setuName(rs.getString("USERNAME"));
	                userInfo.setPassword(rs.getString("PASSWORD"));
	                userInfo.setEnabled(rs.getString("ENABLED"));
	                userInfo.setRole(rs.getString("ROLE"));
	                users.add(userInfo);	               
	            }
	 
	            return users;
	        }
	 
	    });

	}
	
	@Override
	public UserInfo getUserInfo(String uname) {

		String sql = "SELECT u.USERNAME, u.PASSWORD, a.ROLE FROM "+
			     "USERS u INNER JOIN USER_ROLES a on u.USERNAME=a.USERNAME WHERE "+
			     "u.ENABLED ='Y' and u.USERNAME =" + "'" + uname + "'";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<UserInfo>() {
			 
	        public UserInfo extractData(ResultSet rs) throws SQLException,
	                DataAccessException {
	            if (rs.next()) {
	                UserInfo userInfo = new UserInfo();
	                userInfo.setuName(rs.getString("USERNAME"));
	                userInfo.setPassword(rs.getString("PASSWORD"));
	                userInfo.setRole(rs.getString("ROLE"));
	               
	                return userInfo;
	            }
	 
	            return null;
	        }
	 
	    });

	}

}
