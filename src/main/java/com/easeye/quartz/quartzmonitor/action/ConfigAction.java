package com.easeye.quartz.quartzmonitor.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.core.jmx.JobDataMapSupport;

import com.easeye.quartz.quartzmonitor.conf.QuartzConfig;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectService;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectServiceImpl;
import com.easeye.quartz.quartzmonitor.core.QuartzInstanceContainer;
import com.easeye.quartz.quartzmonitor.object.Job;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Result;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.object.Trigger;
import com.easeye.quartz.quartzmonitor.service.JobService;
import com.easeye.quartz.quartzmonitor.service.SchedulerService;
import com.easeye.quartz.quartzmonitor.service.TriggerService;
import com.easeye.quartz.quartzmonitor.service.impl.JobServiceImpl;
import com.easeye.quartz.quartzmonitor.service.impl.SchedulerServiceImpl;
import com.easeye.quartz.quartzmonitor.service.impl.TriggerServiceImpl;
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
	private String schedulerName ;
	
	private SchedulerService schedulerService = new SchedulerServiceImpl();
	
	private Map<String,QuartzConfig> quartzMap;
	
	private JobService jobService = new JobServiceImpl();
	private TriggerService triggerService = new TriggerServiceImpl();
	
	public String sync(){
		String resp = null ;
		if(StringUtils.isBlank(host) || port<1 || port > 65535 ){
			resp = "host&port参数错误" ;
		}else{
			try {
				Scheduler scheduler = schedulerService.getSchedulerByHost(host, port, schedulerName) ;
				//config
				QuartzConfig config = new QuartzConfig();
				config.setUuid(scheduler.getQuartzInstanceUUID());
				config.setHost(scheduler.getHost());
				config.setPort(scheduler.getPort());
				config.setName(scheduler.getName());
				config.setUserName(scheduler.getUserName());
				config.setPassword(scheduler.getPassword());
				//quartz-conn
				QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
				QuartzInstance quartzInstance = quartzConnectService.initInstance(config);
				QuartzInstanceContainer.addQuartzInstance(config.getUuid(), quartzInstance);
				//schedulers on remote-host
				List<Scheduler> schedulers = quartzInstance.getSchedulerList();
				log.info(" schedulers list size:" + schedulers.size());
				if (schedulers != null && schedulers.size() > 0) {
					for (int i = 0; i < schedulers.size(); i++) {
						Scheduler schd = schedulers.get(i);
						if(StringUtils.isBlank(schedulerName) || schedulerName.equals(schd.getName())){
							this.syncScheduler(quartzInstance, schd);
						}
					}
				}
				resp = "同步任务成功" ;
			} catch (Exception e) {
				log.error("sync error",e);
				resp = "同步任务失败" ;
			}
			
		}
		try {
			JsonUtil.toJson(new Gson().toJson(resp)) ;
		} catch (Exception e) {
		}
		return null ;
	}
	
	private void syncScheduler(QuartzInstance quartzInstance,Scheduler scheduler) throws Exception{
		List<Job> nativeJobs = jobService.getALLJobs(scheduler.getQuartzInstanceUUID());
		List<Job> remoteJobs = quartzInstance.getJmxAdapter().getJobDetails(quartzInstance, scheduler);
		for (Job nativeJob : nativeJobs) {
			boolean nativeJobExistInRemote = false;
			//job-sync
			for (Job remoteJob :remoteJobs) {
				if (remoteJob.getJobName().equals(nativeJob.getJobName()) && remoteJob.getGroup().equals(nativeJob.getGroup())) {
					nativeJobExistInRemote = true;
					break;
				}
			}
			//job-unsync,add job remote
			if (!nativeJobExistInRemote) {
				nativeJob.setSchedulerName(scheduler.getName());
				addJobRemote(nativeJob);
			}
		}
	}
	
	/**
	 * 在服务端添加一个job
	 * @param nativeJob
	 * @throws Exception
	 */
	private void addJobRemote(Job nativeJob) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", nativeJob.getJobName());
		map.put("group", nativeJob.getGroup());
		map.put("description", nativeJob.getDescription());
		map.put("jobClass", nativeJob.getJobClass());
		
		if(null!=nativeJob.getJobDataMap()){
			map.put("jobDataMap",  JobDataMapSupport.newJobDataMap(nativeJob.getJobDataMap()));   //job需要的参数
		}
		
		map.put("durability", true);
		map.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");
		
		QuartzInstance instance = Tools.getQuartzInstance(nativeJob.getQuartzInstanceId());
		instance.getJmxAdapter().addJob(instance, instance.getSchedulerByName(nativeJob.getSchedulerName()), map);
		
		List<Trigger> triggers = triggerService.getALLTriggers(nativeJob.getUuid());
		for (Trigger trigger : triggers) {
			trigger.setJobName(nativeJob.getJobName());
			addTriggerRemote(trigger, nativeJob);
		}
	}
	
	private void addTriggerRemote(Trigger trigger, Job nativeJob) throws Exception {
		HashMap<String, Object> triggerMap = new HashMap<String, Object>();
		triggerMap.put("name", trigger.getName());
		triggerMap.put("group",trigger.getGroup());
		triggerMap.put("description", trigger.getDescription());
		triggerMap.put("cronExpression", trigger.getCronExpression());
		triggerMap.put("triggerClass", "org.quartz.impl.triggers.CronTriggerImpl");
		triggerMap.put("jobName", trigger.getJobName());
		triggerMap.put("jobGroup", trigger.getGroup());
		
		QuartzInstance instance = Tools.getQuartzInstance(nativeJob.getQuartzInstanceId());
		instance.getJmxAdapter().addTriggerForJob(instance, instance.getSchedulerByName(nativeJob.getSchedulerName()), nativeJob,triggerMap);
	}

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

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	
}