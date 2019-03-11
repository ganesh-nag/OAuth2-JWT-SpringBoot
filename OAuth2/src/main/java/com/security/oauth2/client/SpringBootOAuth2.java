package com.security.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"com.security.oauth2"})
public class SpringBootOAuth2 {

	public static void main(String[] args) {
		
        SpringApplication.run(SpringBootOAuth2.class, args);

	}

}
