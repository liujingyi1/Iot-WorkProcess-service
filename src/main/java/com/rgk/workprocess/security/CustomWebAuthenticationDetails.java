package com.rgk.workprocess.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
/**
 *  WebAuthenticationDetails:该类提供了获取用户登录时携带的额外信息的功能，默认提供了 remoteAddress 与 sessionId 信息。
 *
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = 1L;
	//验证码
	private String verifyCode;
	
	// 将form 表单中的 verifyCode 获取到，并通过 get 方法方便被调用
	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		verifyCode = request.getParameter("verifyCode");
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
        return sb.toString();
	}
	
	

	
}
