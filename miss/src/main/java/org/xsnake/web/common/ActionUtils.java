package org.xsnake.web.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.xsnake.dao.Condition;

public class ActionUtils {
	
	public static void requestToCondition(Condition condition, HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String key = names.nextElement();
			String value = request.getParameter(key);
			condition.set(key, value);
		}
	}

}
