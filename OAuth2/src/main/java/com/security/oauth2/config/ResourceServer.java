package com.security.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter{
	
	@Value("${security.oauth2.resource.id}")
    private String resourceId;

	@Autowired
	TokenStore tokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resourceConfigurer) {
		
		resourceConfigurer.resourceId(resourceId)
		.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception{
		
		http.anonymous().disable()
		.authorizeRequests()		
		.antMatchers("/api/oauth2/users").access("hasRole('ADMIN')")
		.antMatchers("/api/oauth2/users/**").authenticated()
		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
