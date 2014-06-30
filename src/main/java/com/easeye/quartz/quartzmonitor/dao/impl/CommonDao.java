package com.easeye.quartz.quartzmonitor.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.easeye.quartz.quartzmonitor.exception.DBException;
import com.easeye.quartz.quartzmonitor.object.DataSourceInfo;
import com.easeye.quartz.quartzmonitor.util.DataSourceMapUtil;

public class CommonDao {
	private DataSourceInfo dsObj;

	public CommonDao(DataSourceInfo dsObj) {
		this.dsObj = dsObj;
	}
	
	protected void executeSQL(String sql, Object... params) throws DBException, SQLException {

		QueryRunner queryRunner = new QueryRunner(true);
		Connection conn = null;
		try {
			conn = DataSourceMapUtil.getConnection(this.dsObj);
			queryRunner.update(conn, sql, params);
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			throw new DBException("执行SQL: " + sql + "失败: " + e.getMessage(), e);
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	}

	public DataSourceInfo getDsObj() {
		return dsObj;
	}

	public void setDsObj(DataSourceInfo dsObj) {
		this.dsObj = dsObj;
	}
}
