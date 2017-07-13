package org.xsnake.cloud.common.exception;

/**
 * 程序开发时必须解决的异常
 * @author Jerry.Zhao
 *
 */
public class BugException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BugException(){}
	
	public BugException(Exception e){
		super(e);
	}
	
	public BugException(String message){
		super(message);
	}
	
}
