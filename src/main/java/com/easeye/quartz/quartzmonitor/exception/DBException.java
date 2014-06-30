package com.easeye.quartz.quartzmonitor.exception;

public class DBException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8475835573950967169L;

	public DBException() {
		
	}
	
	public DBException(String message) {
		super(message);
	}
	
	public DBException(String message, Exception ex) {
		super(message, ex);
	}
}
