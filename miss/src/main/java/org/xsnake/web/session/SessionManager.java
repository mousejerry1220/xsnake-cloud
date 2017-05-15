package org.xsnake.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xsnake.web.permission.IUser;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 上午11:47:30 
* 
*/
public class SessionManager implements SessionSupport{

	public final static String LOGIN_USER = "LOGIN_USER";

	public void setSessionAttribute(String key , Object obj){
		getSession().setAttribute(key, obj);
	}
	
	public void sessionInvalidate(){
		getSession().invalidate();
	}
	
	public Object getSessionAttribute(String key){
		return getSession().getAttribute(key);
	}
	
	public void removeAttribute(String key){
		 getSession().removeAttribute(key);
	}
	
	public static HttpSession getSession(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession();
	}
	
	public static IUser getLoginUser(){
		return (IUser)getSession().getAttribute(LOGIN_USER);
	}
	
	public static void setLoginUser(IUser user){
		getSession().setAttribute(LOGIN_USER,user);
	}
	
}
