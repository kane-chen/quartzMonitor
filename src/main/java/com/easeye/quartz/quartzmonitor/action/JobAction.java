package com.easeye.quartz.quartzmonitor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.core.jmx.JobDataMapSupport;

import com.easeye.quartz.quartzmonitor.core.JobContainer;
import com.easeye.quartz.quartzmonitor.core.QuartzInstanceContainer;
import com.easeye.quartz.quartzmonitor.object.Job;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Result;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.object.Trigger;
import com.easeye.quartz.quartzmonitor.service.JobService;
import com.easeye.quartz.quartzmonitor.service.TriggerService;
import com.easeye.quartz.quartzmonitor.service.impl.JobServiceImpl;
import com.easeye.quartz.quartzmonitor.service.impl.TriggerServiceImpl;
import com.easeye.quartz.quartzmonitor.util.JsonUtil;
import com.easeye.quartz.quartzmonitor.util.Tools;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class JobAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(JobAction.class);
	
	private String uuid;  //quartzInstanceId
	private String jobuuid;  //job的uuid
	private List<Job> jobList = new ArrayList<Job>();
	private Map<String, Job> jobMap;
	private Job job;
	private Set<String> jobSet = new HashSet<String>();
	private Map<String, String> schedulerMap = new HashMap<String, String>();
	private Map<String, Object> jobDataMap = new HashMap<String, Object>() ;
	private List<String> jobDataMapKey = new ArrayList<String>();
	private List<String> jobDataMapValue = new ArrayList<String>();
	
	private Integer pageNum = 1;// 当前页数
	private Integer numPerPage = 20;// 每页的数量
	private Integer pageCount;// 总页数
	private Integer size;

	private JobService jobService = new JobServiceImpl();
	private TriggerService triggerService = new TriggerServiceImpl();
	
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
	
	/**
	 * 在服务端添加一个本机job
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
	
	/**
	 * 把本地job同步到服务端
	 * @throws Exception
	 */
	private void syncJobs() throws Exception {
		Map<String, QuartzInstance> quartzInstanceMap = QuartzInstanceContainer.getQuartzInstanceMap();
		for (Map.Entry<String, QuartzInstance> entry : quartzInstanceMap.entrySet()) {
			QuartzInstance instance = entry.getValue();
			List<Scheduler> schedulers = instance.getSchedulerList();
			log.info(" schedulers list size:" + schedulers.size());
			if (schedulers != null && schedulers.size() > 0) {
				for (int i = 0; i < schedulers.size(); i++) {
					Scheduler scheduler = schedulers.get(i);
					List<Job> nativeJobs = jobService.getALLRunJobs(scheduler.getQuartzInstanceUUID());
					List<Job> remoteJobs = instance.getJmxAdapter().getJobDetails(instance, scheduler);
					for (Job nativeJob : nativeJobs) {
						boolean nativeJobExistInRemote = false;
						for (Job remoteJob :remoteJobs) {
							if (remoteJob.getJobName().equals(nativeJob.getJobName()) && remoteJob.getGroup().equals(nativeJob.getGroup())) {
								nativeJobExistInRemote = true;
								break;
							}
						}
						
						if (!nativeJobExistInRemote) {
							nativeJob.setSchedulerName(scheduler.getName());
							addJobRemote(nativeJob);
						}
					}
				}
			}
		}
	}
	
	public String list() throws Exception {

//		QuartzInstance instance = Tools.getQuartzInstance();
		
		syncJobs();
		
		Map<String, QuartzInstance> quartzInstanceMap = QuartzInstanceContainer.getQuartzInstanceMap();
		for (Map.Entry<String, QuartzInstance> entry : quartzInstanceMap.entrySet()) {
			QuartzInstance instance = entry.getValue();
			List<Scheduler> schedulers = instance.getSchedulerList();
			log.info(" schedulers list size:" + schedulers.size());
			if (schedulers != null && schedulers.size() > 0) {
				for (int i = 0; i < schedulers.size(); i++) {
					Scheduler scheduler = schedulers.get(i);
					List<Job> localJobsInDb = jobService.getALLJobs(scheduler.getQuartzInstanceUUID());
					List<Job> remoteJobsByJMX = instance.getJmxAdapter().getJobDetails(instance, scheduler);
					Map<String,Job> remoteJobsMap = new HashMap<String,Job>() ;
					if(null!=remoteJobsByJMX){
						for(Job remoteJob : remoteJobsByJMX){
							if(null!=remoteJob){
								remoteJobsMap.put(remoteJob.getJobName()+"_"+remoteJob.getGroup(), remoteJob) ;
							}
						}
					}
					for(Job nativeJob : localJobsInDb){
						if(null!=nativeJob){
							String mapKey = nativeJob.getJobName()+"_"+nativeJob.getGroup() ;
							Job trueJob = remoteJobsMap.get(mapKey) ;
							if(null!=trueJob){
								trueJob.setUuid(nativeJob.getUuid());
								if(null == trueJob.getJobDataMap()){
									trueJob.setJobDataMap(nativeJob.getJobDataMap());
								}
								JobContainer.addJob(trueJob.getUuid(), trueJob);
								jobList.add(trueJob);
								trueJob.setJobStatus(Job.STATUS_RUN);
							}else{
								if(Job.STATUS_RUN == nativeJob.getJobStatus()){
									nativeJob.setJobStatus(Job.STATUS_STOP);
								}
								jobList.add(nativeJob) ;
							}
						}
					}
				}
			}
		}
		

		pageCount = Tools.getPageSize(jobList.size(), numPerPage);
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageNum > pageCount) {
			pageNum = pageCount;
		}
		log.info("job size:" + jobList.size());
		size = jobList.size();
		return "list";
	}

	public String start() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance(uuid);
		
		Job job = JobContainer.getJobById(jobuuid);
		instance.getJmxAdapter().startJobNow(instance, instance.getSchedulerByName(job.getSchedulerName()), job);

		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String delete() throws Exception {

		jobService.deleteJob(jobuuid);
		Job job = JobContainer.getJobById(jobuuid);
		QuartzInstance instance = Tools.getQuartzInstance(job.getQuartzInstanceId());
		JobContainer.removeJobById(jobuuid);
		log.info("delete a quartz job!");
		instance.getJmxAdapter().deleteJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		Result result = new Result();
		result.setMessage("删除成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String pause() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance(uuid);

		Job job = JobContainer.getJobById(jobuuid);
		jobService.updateJob(job.getUuid(), Job.STATUS_PAUSE);
		log.info("pause a quartz job!");
		instance.getJmxAdapter().pauseJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		Result result = new Result();
		result.setMessage("Job已暂停");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String stop() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance(uuid);

		Job job = JobContainer.getJobById(jobuuid);
		jobService.updateJob(job.getUuid(), Job.STATUS_STOP);
		log.info("stop a quartz job!");
		instance.getJmxAdapter().deleteJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);

		Result result = new Result();
		result.setMessage("Job已中止");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String restart() throws Exception {

		Job myJob = JobContainer.getJobById(jobuuid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", myJob.getJobName());
		map.put("group", myJob.getGroup());
		map.put("description", myJob.getDescription());
		map.put("jobClass", myJob.getJobClass());
		map.put("jobDataMap", myJob.getJobDataMap());
		map.put("durability", true);
		map.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");
		
		QuartzInstance instance = Tools.getQuartzInstance(uuid);
		instance.getJmxAdapter().updateJob(instance, instance.getSchedulerByName(myJob.getSchedulerName()), map);
		
		jobService.updateJob(job.getUuid(), Job.STATUS_RUN);
		log.info("restart a quartz job!");
		
		Result result = new Result();
		result.setMessage("修改成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
		
	}

	public String resume() throws Exception {
		
		QuartzInstance instance = Tools.getQuartzInstance(uuid);
		
		Job job = JobContainer.getJobById(jobuuid);
		jobService.updateJob(job.getUuid(), Job.STATUS_RUN);
		log.info("resume a quartz job!");
		instance.getJmxAdapter().resumeJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		
		Result result = new Result();
		result.setMessage("Job已恢复");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String show() throws Exception {

		Map<String, QuartzInstance> quartzInstanceMap = QuartzInstanceContainer.getQuartzInstanceMap();
		for (Map.Entry<String, QuartzInstance> entry : quartzInstanceMap.entrySet()) {
			QuartzInstance instance = entry.getValue();
			List<Scheduler> schedulers = instance.getSchedulerList();
			for (Scheduler scheduler : schedulers) {
//				jobSet.add(scheduler.getName());
				schedulerMap.put(instance.getUuid(), scheduler.getName());
			}
		}
		
		return "add";
	}

	public String add() throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", job.getJobName());
		map.put("group", job.getGroup());
		map.put("description", job.getDescription());
		map.put("jobClass", job.getJobClass());
		
		if(jobDataMapKey.size() > 0){
			Map<String, Object> parammap = new HashMap<String, Object>();
			for (int i=0; i<jobDataMapKey.size(); i++) {
				parammap.put(jobDataMapKey.get(i), jobDataMapValue.get(i));
			}
			
			job.setJobDataMap(parammap);
			map.put("jobDataMap", job.getJobDataMap());   //job需要的参数
		}
		
		map.put("durability", true);
		map.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");
		
		String curuuid = job.getUuid();
		job.setUuid(Tools.generateUUID());
		job.setQuartzInstanceId(curuuid);
		QuartzInstance instance = Tools.getQuartzInstance(curuuid);
		instance.getJmxAdapter().addJob(instance, instance.getSchedulerByName(job.getSchedulerName()), map);
		
		jobService.addJob(job);
		
		log.info("add job successfully!");

		Result result = new Result();
		result.setMessage("添加成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String edit() throws Exception {

		Job myJob = JobContainer.getJobById(jobuuid);
		job = new Job();
		job.setUuid(myJob.getUuid());
		job.setJobName(myJob.getJobName());
		job.setGroup(myJob.getGroup());
		job.setJobClass(myJob.getJobClass());
		job.setDescription(myJob.getDescription());
		job.setSchedulerName(myJob.getSchedulerName());
		jobDataMap = myJob.getJobDataMap() ;
		return "edit";
	}
	
	public String update() throws Exception {
		Job myJob = JobContainer.getJobById(jobuuid);
		
		myJob.setJobClass(job.getJobClass());
		myJob.setDescription(job.getDescription());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", myJob.getJobName());
		map.put("group", myJob.getGroup());
		map.put("description", myJob.getDescription());
		map.put("jobClass", myJob.getJobClass());
		//jobDataMap
		if(jobDataMapKey.size() > 0){
			Map<String, Object> parammap = new HashMap<String, Object>();
			for (int i=0; i<jobDataMapKey.size(); i++) {
				parammap.put(jobDataMapKey.get(i), jobDataMapValue.get(i));
			}
			
			myJob.setJobDataMap(parammap);
			map.put("jobDataMap", myJob.getJobDataMap());
		}
		map.put("durability", true);
		map.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");
		
		QuartzInstance instance = Tools.getQuartzInstance(myJob.getQuartzInstanceId());
		instance.getJmxAdapter().updateJob(instance, instance.getSchedulerByName(myJob.getSchedulerName()), map);
		
		jobService.updateJob(myJob);
		
		Result result = new Result();
		result.setMessage("修改成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
 	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getJobuuid() {
		return jobuuid;
	}

	public void setJobuuid(String jobuuid) {
		this.jobuuid = jobuuid;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Map<String, Job> getJobMap() {
		return jobMap;
	}

	public void setJobMap(Map<String, Job> jobMap) {
		this.jobMap = jobMap;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Set<String> getJobSet() {
		return jobSet;
	}

	public void setJobSet(Set<String> jobSet) {
		this.jobSet = jobSet;
	}

	public Map<String, String> getSchedulerMap() {
		return schedulerMap;
	}

	public void setSchedulerMap(Map<String, String> schedulerMap) {
		this.schedulerMap = schedulerMap;
	}

	public List<String> getJobDataMapKey() {
		return jobDataMapKey;
	}

	public void setJobDataMapKey(List<String> jobDataMapKey) {
		this.jobDataMapKey = jobDataMapKey;
	}

	public List<String> getJobDataMapValue() {
		return jobDataMapValue;
	}

	public void setJobDataMapValue(List<String> jobDataMapValue) {
		this.jobDataMapValue = jobDataMapValue;
	}

	public Map<String, Object> getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(Map<String, Object> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

}