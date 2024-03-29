package com.rgk.workprocess.security;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
/**
   *   该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 *
 */
@Slf4j
@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
		log.info("CustomAuthenticationDetailsSource request = {}", request);
		log.info("security request = {}", request);
		return new CustomWebAuthenticationDetails(request);
	}

}
