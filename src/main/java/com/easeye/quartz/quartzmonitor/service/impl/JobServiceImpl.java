package com.easeye.quartz.quartzmonitor.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.conf.JdbcConfig;
import com.easeye.quartz.quartzmonitor.dao.JobDao;
import com.easeye.quartz.quartzmonitor.dao.impl.JobDaoImpl;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Job;
import com.easeye.quartz.quartzmonitor.service.JobService;

public class JobServiceImpl implements JobService {

	private JobDao jobDao;
	
	public JobServiceImpl() {
		DataSourceInfo dsInfo = new DataSourceInfo();
		dsInfo.setConnurl(JdbcConfig.getJdbcUrl());
		dsInfo.setDriverclass(JdbcConfig.getJdbcDriverClass());
		dsInfo.setDsusername(JdbcConfig.getJdbcUserName());
		dsInfo.setDspassword(JdbcConfig.getJdbcPassword());
		
		jobDao = new JobDaoImpl(dsInfo);
	}
	
	@Override
	public void addJob(Job job) throws DBException, SQLException {
		jobDao.addJob(job);
	}

	@Override
	public void updateJob(Job job) throws DBException, SQLException {
		jobDao.updateJob(job);
	}

	@Override
	public void deleteJob(String jobId) throws DBException, SQLException {
		jobDao.deleteJob(jobId);
	}

	@Override
	public List<Job> getALLJobs(String schedulerId) throws DBException, SQLException {
		return jobDao.getALLJobs(schedulerId,-1);
	}
	
	@Override
	public List<Job> getALLRunJobs(String schedulerId) throws DBException, SQLException {
		return jobDao.getALLJobs(schedulerId,Job.STATUS_RUN);
	}

	@Override
	public void updateJob(String jobId, int status) throws DBException,
			SQLException {
		jobDao.updateJob(jobId, status);
	}

}
