package com.apps.developerblog.app.ws.security;

import com.apps.developerblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 864000000; // 10 DAYS
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SING_UP_URL = "/users";
//	public static final String TOKEN_SECRET = "jf9i4jgu83nf10";
	
	public static String getTokenSecret() {
		
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		
		return appProperties.getTokenSecret();
	}
	
}