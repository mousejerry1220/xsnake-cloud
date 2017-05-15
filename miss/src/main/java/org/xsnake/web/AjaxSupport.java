package org.xsnake.web;

public interface AjaxSupport {

	String sendSuccessMessage();
	
	String sendSuccessMessage(Object result);
	
	String sendErrorMessage();
	
	String sendErrorMessage(Object result);
	
}
