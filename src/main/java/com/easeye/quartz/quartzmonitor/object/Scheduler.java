package com.easeye.quartz.quartzmonitor.object;

import javax.management.ObjectName;

public class Scheduler {

	private String quartzInstanceUUID;
	private String name;
	private ObjectName objectName;

	private String instanceId;
	private boolean started;
	private boolean shutdown;
	private boolean standByMode;
	private String version;
	private String jobStoreClassName;
	private String threadPoolClassName;
	private int threadPoolSize;

	private String host;
	private int port;
	private String userName;
	private String password;
	
	public String getUuidInstance() {
		return this.quartzInstanceUUID + "@@" + instanceId;
	}

	public String getQuartzInstanceUUID() {
		return quartzInstanceUUID;
	}

	public void setQuartzInstanceUUID(String quartzInstanceUUID) {
		this.quartzInstanceUUID = quartzInstanceUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public boolean isStarted() {
		return started;
	}

	public ObjectName getObjectName() {
		return objectName;
	}

	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}

	public boolean isStandByMode() {
		return standByMode;
	}

	public void setStandByMode(boolean standByMode) {
		this.standByMode = standByMode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getJobStoreClassName() {
		return jobStoreClassName;
	}

	public void setJobStoreClassName(String jobStoreClassName) {
		this.jobStoreClassName = jobStoreClassName;
	}

	public String getThreadPoolClassName() {
		return threadPoolClassName;
	}

	public void setThreadPoolClassName(String threadPoolClassName) {
		this.threadPoolClassName = threadPoolClassName;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}