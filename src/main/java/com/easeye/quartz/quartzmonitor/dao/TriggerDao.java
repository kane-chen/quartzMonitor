package com.easeye.quartz.quartzmonitor.dao;

import java.sql.SQLException;
import java.util.List;

import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.Trigger;

public interface TriggerDao {
	/**
	 * 获取某个job下的所有trigger
	 * @return
	 * @throws DBException 
	 * @throws SQLException 
	 */
	public List<Trigger> getALLTriggers(String jobId) throws DBException, SQLException;
	/**
	 * 添加一个trigger
	 * @param trigger
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void addTrigger(Trigger trigger) throws DBException, SQLException;
	/**
	 * 更新一个trigger
	 * @param trigger
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void updateTrigger(Trigger trigger) throws DBException, SQLException;
	/**
	 * 删除一个trigger
	 * @param trigger
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void deleteTrigger(String triggerId) throws DBException, SQLException;
}
