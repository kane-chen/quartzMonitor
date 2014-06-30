package com.easeye.quartz.quartzmonitor.object;

public class DataSourceInfo {

	private String driverclass;
	private String datasourcekey;
	private String connurl; 
	private String dsusername; 
	private String dspassword;
	
	public DataSourceInfo() {
		super();
	}

	public DataSourceInfo(String driverclass, String connurl, String dsusername, String dspassword) {
		super();
		this.driverclass = driverclass;
		this.connurl = connurl;
		this.dsusername = dsusername;
		this.dspassword = dspassword;
	}
	
	public String getDatasourcekey() {
		return datasourcekey;
	}
	public void setDatasourcekey(String datasourcekey) {
		this.datasourcekey = datasourcekey;
	}
	public String getConnurl() {
		return connurl;
	}
	public void setConnurl(String connurl) {
		this.connurl = connurl;
	}
	public String getDsusername() {
		return dsusername;
	}
	public void setDsusername(String dsusername) {
		this.dsusername = dsusername;
	}
	public String getDspassword() {
		return dspassword;
	}
	public void setDspassword(String dspassword) {
		this.dspassword = dspassword;
	}
	public String getDriverclass() {
		return driverclass;
	}
	public void setDriverclass(String driverclass) {
		this.driverclass = driverclass;
	}
	
}