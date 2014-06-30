package com.easeye.quartz.quartzmonitor.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.easeye.quartz.quartzmonitor.dao.TriggerDao;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Trigger;
import com.easeye.quartz.quartzmonitor.util.DataSourceMapUtil;
import com.easeye.quartz.quartzmonitor.util.JdbcUtil;

public class TriggerDaoImpl extends CommonDao implements TriggerDao {

	public TriggerDaoImpl(DataSourceInfo dsObj) {
		super(dsObj);
	}

	@Override
	public void addTrigger(Trigger trigger) throws DBException, SQLException {
		String sql = "INSERT INTO t_trigger ( triggerId ,  jobId ,  name ,  description ,  `group` ,  cronexpr ) ";
		sql += " values (?, ?, ?, ?, ?, ?)";
		
		JdbcUtil.operaterDebugSql(sql, trigger.getUuid(), trigger.getJobId(), trigger.getName(), 
				trigger.getDescription(), trigger.getGroup(), trigger.getCronExpression());
		
		executeSQL(sql, trigger.getUuid(), trigger.getJobId(), trigger.getName(), 
				trigger.getDescription(), trigger.getGroup(), trigger.getCronExpression());
	}

	@Override
	public void updateTrigger(Trigger trigger) throws DBException, SQLException {
		String sql = "UPDATE t_trigger SET ";
		sql += " jobId  = ?, ";
		sql += " name  = ?, ";
		sql += " description  = ?, ";
		sql += " `group`  = ?, ";
		sql += " cronexpr  = ? where  triggerId  = ?";
		
		JdbcUtil.operaterDebugSql(sql, trigger.getJobId(), trigger.getName(), 
				trigger.getDescription(), trigger.getGroup(), trigger.getCronExpression(),
				trigger.getUuid());
		
		executeSQL(sql, trigger.getJobId(), trigger.getName(), 
				trigger.getDescription(), trigger.getGroup(), trigger.getCronExpression(),
				trigger.getUuid());
	}

	@Override
	public void deleteTrigger(String triggerId) throws DBException, SQLException {
		String sql = "DELETE FROM t_trigger WHERE  triggerId  = ?";
		
		JdbcUtil.operaterDebugSql(sql, triggerId);
		
		executeSQL(sql, triggerId);
	}

	@Override
	public List<Trigger> getALLTriggers(String jobId) throws DBException, SQLException {
		String sql = "SELECT  triggerId  AS  uuid ,  jobId ,  name ,  description ,  `group` ,  cronexpr  AS  cronExpression  FROM t_trigger where  jobId  = ?";
		
		JdbcUtil.operaterDebugSql(sql, jobId);
		
		QueryRunner queryRunner = new QueryRunner(true);
		Connection conn = null;
		List<Trigger> list = new ArrayList<Trigger>(0);
		
		try {
			conn = DataSourceMapUtil.getConnection(this.getDsObj());
			list = queryRunner.query(conn, sql, new BeanListHandler<Trigger>(Trigger.class), jobId);
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
