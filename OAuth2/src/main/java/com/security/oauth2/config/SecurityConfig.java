package com.security.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.oauth2.service.UserDetailDBService;

@Configuration
@EnableWebSecurity
@PropertySource(value = { "classpath:application.properties" })
@Order(Ordered.LOWEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().disable()
		.anonymous().disable()
		.httpBasic().and()
		.authorizeRequests().anyRequest().authenticated();
		
	}

	
	
@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {	
	return new BCryptPasswordEncoder();
}


@Bean
public DataSource dataSource() {
	
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
    dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
    dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
    dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));       
    return dataSource;
}

@Bean
public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);    
    jdbcTemplate.setResultsMapCaseInsensitive(true);
   return jdbcTemplate;
}


@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {	
    return super.authenticationManagerBean();
}


}
