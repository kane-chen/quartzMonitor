package com.easeye.quartz.quartzmonitor.exception;

/**
 * 配置没有发现
 * 	包括配置文件没有被发现和配置项没有被发现
 * @author fengzhihao
 */
public class PropertiesNotFindException extends Exception{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6351834862069324162L;

	public PropertiesNotFindException(String msg) {
    	super(msg);
    }

    public PropertiesNotFindException(String msg, Throwable e) {
    	super(msg, e);
    }

}
