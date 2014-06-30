package com.easeye.quartz.quartzmonitor.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easeye.quartz.quartzmonitor.util.PropertiesUtil;

public class JdbcConfig {
	
	protected static Logger logger = LoggerFactory.getLogger(JdbcConfig.class);
	
	private static String JDBC_CONFIG = SystemConfigFile.JDBC_CONFIG;

	private static String strJdbcDriverClass = null;
	private static String strJdbcUrl = null;
	private static String strJdbcUserName = null;
	private static String strJdbcPassword = null;

	static {
		strJdbcDriverClass = PropertiesUtil.getPropertiesValue(JDBC_CONFIG, "jdbc_driverClass");
		strJdbcUrl = PropertiesUtil.getPropertiesValue(JDBC_CONFIG, "jdbc_url");
		strJdbcUserName = PropertiesUtil.getPropertiesValue(JDBC_CONFIG, "jdbc_username");
		strJdbcPassword = PropertiesUtil.getPropertiesValue(JDBC_CONFIG, "jdbc_password");
	}

	public static String getJdbcDriverClass() {
		return strJdbcDriverClass;
	}

	public static String getJdbcUrl() {
		return strJdbcUrl;
	}

	public static String getJdbcUserName() {
		return strJdbcUserName;
	}

	public static String getJdbcPassword() {
		return strJdbcPassword;
	}
}
