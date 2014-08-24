package com.easeye.quartz.quartzmonitor.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.easeye.quartz.quartzmonitor.dao.JobDao;
import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.object.Job;
import com.easeye.quartz.quartzmonitor.util.DataSourceMapUtil;
import com.easeye.quartz.quartzmonitor.util.JdbcUtil;

public class JobDaoImpl extends CommonDao implements JobDao {

	public JobDaoImpl(DataSourceInfo dsObj) {
		super(dsObj);
	}

	@Override
	public void addJob(Job job) throws DBException, SQLException {
		String sql = "INSERT INTO t_job ( jobId ,  schedulerId ,  jobName ,  `group` ,  jobClass ,  jobDataMap ,  description ) ";
		sql += " values (?, ?, ?, ?, ?, ?, ?)";
		
		JdbcUtil.operaterDebugSql(sql, job.getUuid(), job.getQuartzInstanceId(),
				job.getJobName(), job.getGroup(), job.getJobClass(), job.getJobDataMapJson(), 
				job.getDescription());
		
		executeSQL(sql, job.getUuid(), job.getQuartzInstanceId(),
				job.getJobName(), job.getGroup(), job.getJobClass(), job.getJobDataMapJson(), 
				job.getDescription());
	}

	@Override
	public void updateJob(Job job) throws DBException, SQLException {
		String sql = "UPDATE t_job SET ";
		sql += " schedulerId  = ?, ";
		sql += " jobName  = ?, ";
		sql += " `group`  = ?, ";
		sql += " jobClass  = ?, ";
		sql += " jobDataMap  = ?, ";
		sql += " description  = ? where  jobId  = ?";
		
		JdbcUtil.operaterDebugSql(sql, job.getQuartzInstanceId(),
				job.getJobName(), job.getGroup(), job.getJobClass(), job.getJobDataMapJson(), 
				job.getDescription(), job.getUuid());
		
		executeSQL(sql, job.getQuartzInstanceId(),
				job.getJobName(), job.getGroup(), job.getJobClass(), job.getJobDataMapJson(), 
				job.getDescription(), job.getUuid());
	}

	@Override
	public void updateJob(String jobId,int status) throws DBException, SQLException {
		String sql = "UPDATE t_job SET jobStatus = ? where jobId = ?";
		
		JdbcUtil.operaterDebugSql(sql, status, jobId);
		
		executeSQL(sql, status, jobId);
	}

	@Override
	public void deleteJob(String jobId) throws DBException, SQLException {
		String sql = "DELETE FROM t_job WHERE  jobId  = ?";
		
		JdbcUtil.operaterDebugSql(sql, jobId);
		
		executeSQL(sql, jobId);
	}

	@Override
	public List<Job> getALLJobs(String schedulerId,int status) throws DBException, SQLException {
		
		String sql = "SELECT  jobId  AS  uuid ,  schedulerid  AS  quartzInstanceId ,  jobName ,  `group` ,  "
				+ "jobClass ,  jobName ,  description ,  jobDataMap  AS  jobDataMapJson ,jobStatus FROM t_job where  schedulerid  = ? ";
		QueryRunner queryRunner = new QueryRunner(true);
		Connection conn = null;
		List<Job> list = new ArrayList<Job>(0);
		
		try {
			conn = DataSourceMapUtil.getConnection(this.getDsObj());
			if(-1 == status){
				JdbcUtil.operaterDebugSql(sql, schedulerId);
				list = queryRunner.query(conn, sql, new BeanListHandler<Job>(Job.class), schedulerId);
			}else{
				sql += " and jobStatus = ? " ;
				JdbcUtil.operaterDebugSql(sql, schedulerId , status);
				list = queryRunner.query(conn, sql, new BeanListHandler<Job>(Job.class), schedulerId , status);
			}
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
