package com.easeye.quartz.quartzmonitor.service;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.Job;

public interface JobService {
	/**
	 * 获取某个scheduler下的所有job
	 * @return
	 * @throws DBException 
	 * @throws SQLException 
	 */
	public List<Job> getALLJobs(String schedulerId) throws DBException, SQLException;
	/**
	 * 添加一个job
	 * @param job
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void addJob(Job job) throws DBException, SQLException;
	/**
	 * 更新一个job
	 * @param job
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void updateJob(Job job) throws DBException, SQLException;
	/**
	 * 删除一个job
	 * @param job
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void deleteJob(String jobId) throws DBException, SQLException;
}
