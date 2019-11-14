package com.rgk.workprocess.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class SecruityCfg extends WebSecurityConfigurerAdapter{
	private Log log = LogFactory.getLog(getClass());
	
	protected void configure(HttpSecurity http) throws Exception {
		log.info("------security configure-----");

		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/login","/resources/**").permitAll()
		    //其他地址的访问均需要登录认证
            .anyRequest().permitAll();
//			.anyRequest().authenticated();
//			.and()
//			.logout().logoutSuccessUrl("http://192.168.15.12:8080/logout");
	}
	
	
}
