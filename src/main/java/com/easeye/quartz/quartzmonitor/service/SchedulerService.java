package com.easeye.quartz.quartzmonitor.service;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.Scheduler;

public interface SchedulerService {
	/**
	 * 获取所有的schedulers
	 * @return
	 * @throws DBException 
	 * @throws SQLException 
	 */
	public List<Scheduler> getALLSchedulers() throws DBException, SQLException;
	/**
	 * 添加一个scheduler
	 * @param scheduler
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void addScheduler(Scheduler scheduler) throws DBException, SQLException;
	/**
	 * 更新一个scheduler
	 * @param scheduler
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void updateScheduler(Scheduler scheduler) throws DBException, SQLException;
	/**
	 * 删除一个scheduler
	 * @param schedulerId
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void deleteScheduler(String schedulerId) throws DBException, SQLException;
}
