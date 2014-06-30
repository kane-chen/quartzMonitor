package com.easeye.quartz.quartzmonitor.core;

import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import com.easeye.quartz.quartzmonitor.object.Job;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.object.Trigger;

/**
 * 从JMX获取Quartz信息
 */
public interface QuartzJMXAdapter {
	public String getVersion(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

	public List<Job> getJobDetails(QuartzInstance quartzInstance, Scheduler scheduler) throws Exception;

	public Scheduler getSchedulerById(QuartzInstance quartzInstance, String scheduleID) throws Exception;

	public List<Trigger> getTriggersForJob(QuartzInstance quartzInstance, Scheduler scheduler, String jobName, String groupName) throws Exception;

	public void attachListener(QuartzInstance quartzInstance, String scheduleID) throws Exception;

	public Scheduler getSchedulerByJmx(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

	public void startJobNow(QuartzInstance quartzInstance, Scheduler scheduler, Job job) throws Exception;

	public void pauseJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job) throws Exception;

	public void pauseTrigger(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger) throws Exception;
	
	public void deleteJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job) throws Exception;

	public void addJob(QuartzInstance instance, Scheduler schedulerByName, Map<String, Object> jobMap) throws Exception;
	
	public void updateJob(QuartzInstance instance, Scheduler schedulerByName, Map<String, Object> jobMap) throws Exception;

	public void deleteTrigger(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger) throws Exception;

	public String getTriggerState(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger) throws Exception;

	public void addTriggerForJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job, Map<String, Object> triggerMap) throws Exception;

	public void resumeJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job) throws Exception;
	
	public void resumeTrigger(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger) throws Exception;

}