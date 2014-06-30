package com.easeye.quartz.quartzmonitor.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.conf.JdbcConfig;
import com.easeye.quartz.quartzmonitor.dao.SchedulerDao;
import com.easeye.quartz.quartzmonitor.dao.impl.SchedulerDaoImpl;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.service.SchedulerService;

public class SchedulerServiceImpl implements SchedulerService {

	private SchedulerDao schedulerDao;
	
	public SchedulerServiceImpl() {
		DataSourceInfo dsInfo = new DataSourceInfo();
		dsInfo.setConnurl(JdbcConfig.getJdbcUrl());
		dsInfo.setDriverclass(JdbcConfig.getJdbcDriverClass());
		dsInfo.setDsusername(JdbcConfig.getJdbcUserName());
		dsInfo.setDspassword(JdbcConfig.getJdbcPassword());
		
		schedulerDao = new SchedulerDaoImpl(dsInfo);
	}
	@Override
	public void addScheduler(Scheduler scheduler) throws DBException, SQLException {
		schedulerDao.addScheduler(scheduler);
	}

	@Override
	public void updateScheduler(Scheduler scheduler) throws DBException, SQLException {
		schedulerDao.updateScheduler(scheduler);
	}

	@Override
	public void deleteScheduler(String schedulerId) throws DBException, SQLException {
		schedulerDao.deleteScheduler(schedulerId);
	}
	@Override
	public List<Scheduler> getALLSchedulers() throws DBException, SQLException {
		return schedulerDao.getALLSchedulers();
	}

}
