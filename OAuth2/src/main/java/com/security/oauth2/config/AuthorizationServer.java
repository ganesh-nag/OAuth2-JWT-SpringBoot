package com.security.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter{

	private static final int ACCESS_TOKEN_VALIDITY = 10000;
	private static final int REFRESH_TOKEN_VALIDITY = 30000;
	
	@Value("${security.oauth2.resource.id}")
	private String resourceId;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer)throws Exception {
		
		endpointsConfigurer
		.authenticationManager(this.authenticationManager)
		.tokenStore(tokenStore())		
		.accessTokenConverter(accessTokenConverter());
	}
	
	@Bean
	public TokenStore tokenStore() {
		
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("abcd");
		return converter;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer securityConfigurer)throws Exception {
		
		
		securityConfigurer.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
		.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");

	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer client)throws Exception{
		
		client.inMemory()
		.withClient("trusted-app")
		.authorizedGrantTypes("client_credentials", "password", "refresh_token")
		.authorities("ROLE_TRUSTED_CLIENT")
		.scopes("read", "write")
		.resourceIds(resourceId)
		.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY)
		.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY)
		.secret("secret");
	}
}
