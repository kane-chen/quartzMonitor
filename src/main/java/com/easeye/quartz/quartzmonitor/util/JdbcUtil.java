package com.easeye.quartz.quartzmonitor.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUtil {

	protected static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);
	
	public static void operaterDebugSql(String sql, Object... params){
		logger.debug("SQL: " + sql);
		logger.debug("\nParameters: " + StrUtil.join(",", params));
	}
	
	public static void operaterInfoSql(String sql, Object... params){
		logger.info("SQL: " + sql);
		logger.info("\nParameters: " + StrUtil.join(",", params));
	}
	
	public static void operaterErrorSql(String sql, Object... params){
		logger.error("SQL: " + sql);
		logger.error("\nParameters: " + StrUtil.join(",", params));
	}
	
	
	/**
	 * 功能: 辅助SQL语句中IN函数的拼接
	 * @param fieldName 数据库表中对应的字段名字
	 * @param arrs IN函数中需要传递的参数
	 * */
	public static String getJdbcInFuncStr(String fieldName, Object[] arrs) {
		StringBuilder sb = new StringBuilder();
		int currcount = arrs.length / 1000;
		int remainder = arrs.length % 1000;
		if (remainder > 0) {
			currcount++;
		}
		for (int i = 0; i < currcount; i++) {
			sb.append(fieldName + " in (");
			boolean flag = true;
			if ((i == currcount - 1) && remainder > 0) {
				for (int j = (i * 1000); j < (i * 1000) + remainder; j++) {
					if (flag) {
						sb.append("'"+arrs[j]+"'");
						flag = false;
					} else {
						sb.append(",'").append(arrs[j]).append("'");
					}
				}

				sb.append(")");
			} else {
				for (int j = (i * 1000); j < (i * 1000) + 1000; j++) {
					if (flag) {
						sb.append("'").append(arrs[j]).append("'");
						flag = false;
					} else {
						sb.append(",'").append(arrs[j]+"'");
					}
				}

				sb.append(")");
			}
			if ((i != currcount - 1)) {
				sb.append(" or ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * 功能: 辅助SQL语句中IN函数的拼接
	 * @param fieldName 数据库表中对应的字段名字
	 * @param arrs IN函数中需要传递的参数
	 * @param sign in 还是not in
	 * */
	public static String getJdbcInFuncStrNumber(String fieldName, Object[] arrs, String sign) {
		StringBuilder sb = new StringBuilder();
		int currcount = arrs.length / 1000;
		int remainder = arrs.length % 1000;
		if (remainder > 0) {
			currcount++;
		}
		for (int i = 0; i < currcount; i++) {
			sb.append(fieldName + " "+sign+" (");
			boolean flag = true;
			if ((i == currcount - 1) && remainder > 0) {
				for (int j = (i * 1000); j < (i * 1000) + remainder; j++) {
					if (flag) {
						flag = false;
					} else {
						sb.append(",");
					}
					sb.append(arrs[j]);
				}

				sb.append(")");
			} else {
				for (int j = (i * 1000); j < (i * 1000) + 1000; j++) {
					if (flag) {
						flag = false;
					} else {
						sb.append(",");
					}
					sb.append(arrs[j]);
				}

				sb.append(")");
			}
			if ((i != currcount - 1)) {
				sb.append(" or ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * 功能: 辅助SQL语句中IN函数的拼接
	 * @param fieldName 数据库表中对应的字段名字
	 * @param arrs IN函数中需要传递的参数
	 * @param sign in 还是not in
	 * */
	public static String getJdbcInFuncStrChar(String fieldName, Object[] arrs, String sign) {
		StringBuilder sb = new StringBuilder();
		int currcount = arrs.length / 1000;
		int remainder = arrs.length % 1000;
		if (remainder > 0) {
			currcount++;
		}
		for (int i = 0; i < currcount; i++) {
			sb.append(fieldName + " "+sign+" (");
			boolean flag = true;
			if ((i == currcount - 1) && remainder > 0) {
				for (int j = (i * 1000); j < (i * 1000) + remainder; j++) {
					if (flag) {
						flag = false;
					} else {
						sb.append(",");
					}
					sb.append("'"+arrs[j]+"'");
				}

				sb.append(")");
			} else {
				for (int j = (i * 1000); j < (i * 1000) + 1000; j++) {
					if (flag) {
						flag = false;
					} else {
						sb.append(",");
					}
					sb.append("'"+arrs[j]+"'");
				}

				sb.append(")");
			}
			if ((i != currcount - 1)) {
				sb.append(" or ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * 功能: 辅助SQL语句中INSERT INTO BULK UPDATE语句的INSERT部分数据拼接
	 * @param listparams INSERT后面需要的数据集合
	 * */
	public static String getBulkInsertOrUpdateSql(List<Object[]> listparams){
		StringBuilder sb = new StringBuilder();
		boolean wflag = true;
		for(Object[] objs : listparams){
			boolean nflag = true;
			if(wflag){
				sb.append("(");
				for(Object tmp : objs){
					if(nflag){
						sb.append(tmp);
						nflag = false;
					}else{
						sb.append(",").append(tmp);
					}
				}
				sb.append(")");
				wflag = false;
			}else{
				sb.append(",(");
				for(Object tmp : objs){
					if(nflag){
						
						sb.append(tmp);
						nflag = false;
					}else{
						sb.append(",").append(tmp);
					}
				}
				sb.append(")");
			}
		}
		
		return sb.toString();
	}
	
}