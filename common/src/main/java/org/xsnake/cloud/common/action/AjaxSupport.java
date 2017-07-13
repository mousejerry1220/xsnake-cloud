package org.xsnake.cloud.common.action;

public interface AjaxSupport {
	
	String sendSuccessMessage();

	String sendSuccessMessage(Object result);

	String sendErrorMessage();

	String sendErrorMessage(Object result);

}
