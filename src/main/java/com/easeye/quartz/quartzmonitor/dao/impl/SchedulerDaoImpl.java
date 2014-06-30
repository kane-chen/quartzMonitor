package com.easeye.quartz.quartzmonitor.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.easeye.quartz.quartzmonitor.dao.SchedulerDao;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.util.DataSourceMapUtil;
import com.easeye.quartz.quartzmonitor.util.JdbcUtil;

public class SchedulerDaoImpl extends CommonDao implements SchedulerDao {

	public SchedulerDaoImpl(DataSourceInfo dsInfo) {
		super(dsInfo);
	}
	@Override
	public void addScheduler(Scheduler scheduler) throws DBException, SQLException {
		String sql = "INSERT INTO t_scheduler (schedulerId, name, host, port, userName, password) ";
		sql += " values (?, ?, ?, ?, ?, ?)";
		
		JdbcUtil.operaterDebugSql(sql, scheduler.getQuartzInstanceUUID(), 
				scheduler.getName(), scheduler.getPort(),
				scheduler.getUserName(), scheduler.getPassword());
		
		executeSQL(sql, scheduler.getQuartzInstanceUUID(), 
				scheduler.getName(), scheduler.getHost(), scheduler.getPort(),
				scheduler.getUserName(), scheduler.getPassword());
	}

	@Override
	public void updateScheduler(Scheduler scheduler) throws DBException, SQLException {
		String sql = "UPDATE t_scheduler SET ";
		sql += "name = ?, ";
		sql += "host = ?, ";
		sql += "port = ?, ";
		sql += "userName = ?, ";
		sql += "password = ? where schedulerId = ?";
		
		JdbcUtil.operaterDebugSql(sql, scheduler.getName(), scheduler.getHost(), scheduler.getPort(),
				scheduler.getUserName(), scheduler.getPassword(), scheduler.getQuartzInstanceUUID());
		
		executeSQL(sql, scheduler.getName(), scheduler.getHost(), scheduler.getPort(),
				scheduler.getUserName(), scheduler.getPassword(), scheduler.getQuartzInstanceUUID());
	}

	@Override
	public void deleteScheduler(String schedulerId) throws DBException, SQLException {
		String sql = "DELETE FROM t_scheduler WHERE schedulerId = ?";
		
		JdbcUtil.operaterDebugSql(sql, schedulerId);
		
		executeSQL(sql, schedulerId);
	}
	
	@Override
	public List<Scheduler> getALLSchedulers() throws DBException, SQLException {
		String sql = "SELECT schedulerid AS quartzInstanceUUID, name, host, port, userName, password FROM t_scheduler";
		
		JdbcUtil.operaterDebugSql(sql);
		
		QueryRunner queryRunner = new QueryRunner(true);
		Connection conn = null;
		List<Scheduler> list = new ArrayList<Scheduler>(0);
		
		try {
			conn = DataSourceMapUtil.getConnection(this.getDsObj());
			list = queryRunner.query(conn, sql, new BeanListHandler<Scheduler>(Scheduler.class));
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			throw new DBException("执行SQL: " + sql + "失败: " + e.getMessage(), e);
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		
		return list;
	}

}
