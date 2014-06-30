package com.easeye.quartz.quartzmonitor.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easeye.quartz.quartzmonitor.conf.SystemConfigFile;
import com.easeye.quartz.quartzmonitor.exception.PropertiesNotFindException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceMapUtil {

	public static Logger logger = LoggerFactory.getLogger(DataSourceMapUtil.class);
	
	private static String c3p0_CONFIG = SystemConfigFile.c3p0_CONFIG;
	
	public static Map<String, ComboPooledDataSource> dataSourceMap = new HashMap<String, ComboPooledDataSource>();
	
	private static ComboPooledDataSource createDataSource(DataSourceInfo dsObj) throws SQLException, IOException, PropertiesNotFindException, URISyntaxException {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(dsObj.getDriverclass());
			dataSource.setJdbcUrl(dsObj.getConnurl());
	    	dataSource.setUser(dsObj.getDsusername());
	    	dataSource.setPassword(dsObj.getDspassword());
	    	dataSource=(ComboPooledDataSource) PropertiesUtil.setObjectProperty(dataSource, new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + c3p0_CONFIG));
		} catch (PropertyVetoException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return dataSource;
	}
	
	/**
	 * Java专用
	 * @param dsObj
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws PropertiesNotFindException 
	 * @throws URISyntaxException 
	 * @throws PropertyVetoException 
	 */
	public static synchronized Connection getConnection(DataSourceInfo dsObj) throws SQLException, IOException, PropertiesNotFindException, URISyntaxException {
		ComboPooledDataSource dataSource = dataSourceMap.get(dsObj.getConnurl());
		if (dataSource == null) {
			dataSource = createDataSource(dsObj);
			dataSourceMap.put(dsObj.getConnurl(), dataSource);
		}
		
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}
}