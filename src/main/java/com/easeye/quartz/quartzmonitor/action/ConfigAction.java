package com.easeye.quartz.quartzmonitor.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.easeye.quartz.quartzmonitor.conf.QuartzConfig;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectService;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectServiceImpl;
import com.easeye.quartz.quartzmonitor.core.QuartzInstanceContainer;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Result;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.service.SchedulerService;
import com.easeye.quartz.quartzmonitor.service.impl.SchedulerServiceImpl;
import com.easeye.quartz.quartzmonitor.util.JsonUtil;
import com.easeye.quartz.quartzmonitor.util.Tools;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private  static Logger log = Logger.getLogger(ConfigAction.class);
	
	private String uuid;
	private String host;
	private int port;
	private String username;
	private String password;
	
	private SchedulerService schedulerService = new SchedulerServiceImpl();
	
	private Map<String,QuartzConfig> quartzMap;
	

	public String add() throws Exception {

		String id = Tools.generateUUID();
		QuartzConfig quartzConfig = new QuartzConfig(id, host, port, username, password);
		QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
		QuartzInstance quartzInstance = quartzConnectService.initInstance(quartzConfig);
		//QuartzInstanceService.putQuartzInstance(quartzInstance);
		QuartzInstanceContainer.addQuartzConfig(quartzConfig);
		QuartzInstanceContainer.addQuartzInstance(id, quartzInstance);
		log.info("add a quartz info!");
		
		
		for (Scheduler scheduler : quartzInstance.getSchedulerList()) {
			schedulerService.addScheduler(scheduler);
		}
		
//		XstreamUtil.object2XML(quartzConfig);
		
		Result result = new Result();
		result.setNavTabId("main");
		result.setMessage("添加成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String list() throws Exception {

		quartzMap = QuartzInstanceContainer.getConfigMap();
		
		log.info("get quartz map info.map size:"+quartzMap.size());
		
		return "list";
	}

	
	public String show() throws Exception {

		QuartzConfig quartzConfig = QuartzInstanceContainer.getQuartzConfig(uuid);
		log.info("get a quartz info! uuid:"+uuid);
		uuid = quartzConfig.getUuid();
		host = quartzConfig.getHost();
		port = quartzConfig.getPort();
		username = quartzConfig.getUserName();
		password = quartzConfig.getPassword();
		return "show";
	}
	
	public String update() throws Exception {

		QuartzConfig quartzConfig = new QuartzConfig(uuid,host, port, username,password);
		QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
		QuartzInstance quartzInstance = quartzConnectService.initInstance(quartzConfig);
		QuartzInstanceContainer.addQuartzConfig(quartzConfig);
		QuartzInstanceContainer.addQuartzInstance(uuid, quartzInstance);
		log.info("update a quartz info!");
		
		for (Scheduler scheduler : quartzInstance.getSchedulerList()) {
			schedulerService.updateScheduler(scheduler);
		}
		
//		XstreamUtil.object2XML(quartzConfig);
		
		Result result = new Result();
		result.setMessage("修改成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String delete() throws Exception {

		QuartzInstanceContainer.removeQuartzConfig(uuid);
		QuartzInstanceContainer.removeQuartzInstance(uuid);
		log.info("delete a quartz info!");
		
		schedulerService.deleteScheduler(uuid);
		
//		XstreamUtil.removeXml(uuid);
		
		Result result = new Result();
		result.setMessage("删除成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public Map<String, QuartzConfig> getQuartzMap() {
		return quartzMap;
	}
	public void setQuartzMap(Map<String, QuartzConfig> quartzMap) {
		this.quartzMap = quartzMap;
	}
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
}