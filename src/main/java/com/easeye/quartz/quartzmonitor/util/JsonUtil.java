/*
 * Copyright 2011 Hxrainbow, Co.ltd. All rights reserved.
 * 
 * FileName: JsonUtil.java
 * 
 */

package com.easeye.quartz.quartzmonitor.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * 转换为json的公用类
 */
public class JsonUtil {
	public static void  toJson(String result) throws Exception{
		
		// 设置客户端浏览器输出
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		
		//取消浏览器缓存
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}
}