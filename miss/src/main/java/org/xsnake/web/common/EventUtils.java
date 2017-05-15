package org.xsnake.web.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 下午12:46:27 
* 
*/
public class EventUtils {

	public static void publishEvent(ApplicationEvent event){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		context.publishEvent(event);
	}
}
