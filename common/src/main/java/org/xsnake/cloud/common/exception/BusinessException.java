package org.xsnake.cloud.common.exception;

/**
 * 业务逻辑异常。
 * @author Jerry.Zhao
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(){}
	
	public BusinessException(Exception e){
		super(e);
	}
	
	public BusinessException(String message){
		super(message);
	}
	
}
