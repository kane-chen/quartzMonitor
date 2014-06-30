package com.easeye.quartz.quartzmonitor.conf;

import com.easeye.quartz.quartzmonitor.util.PropertiesUtil;

public class AuthenticationConfig {
	private static String AUTHENTICATION_CONFIG = SystemConfigFile.AUTHENTICATION_CONFIG;
	
	private static String username;
	private static String password;
	
	static {
		username = PropertiesUtil.getPropertiesValue(AUTHENTICATION_CONFIG, "username");
		password = PropertiesUtil.getPropertiesValue(AUTHENTICATION_CONFIG, "password");
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
}
