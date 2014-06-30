package com.easeye.quartz.quartzmonitor.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.conf.JdbcConfig;
import com.easeye.quartz.quartzmonitor.dao.TriggerDao;
import com.easeye.quartz.quartzmonitor.dao.impl.TriggerDaoImpl;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Trigger;
import com.easeye.quartz.quartzmonitor.service.TriggerService;

public class TriggerServiceImpl implements TriggerService {

	private TriggerDao triggerDao;
	
	public TriggerServiceImpl() {
		DataSourceInfo dsInfo = new DataSourceInfo();
		dsInfo.setConnurl(JdbcConfig.getJdbcUrl());
		dsInfo.setDriverclass(JdbcConfig.getJdbcDriverClass());
		dsInfo.setDsusername(JdbcConfig.getJdbcUserName());
		dsInfo.setDspassword(JdbcConfig.getJdbcPassword());
		
		triggerDao = new TriggerDaoImpl(dsInfo);
	}
	
	@Override
	public void addTrigger(Trigger trigger) throws DBException, SQLException {
		triggerDao.addTrigger(trigger);
	}

	@Override
	public void updateTrigger(Trigger trigger) throws DBException, SQLException {
		triggerDao.updateTrigger(trigger);
	}

	@Override
	public void deleteTrigger(String triggerId) throws DBException, SQLException {
		triggerDao.deleteTrigger(triggerId);
	}

	@Override
	public List<Trigger> getALLTriggers(String jobId) throws DBException, SQLException {
		return triggerDao.getALLTriggers(jobId);
	}

}
