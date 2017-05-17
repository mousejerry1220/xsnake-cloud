package org.xsnake.web.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.permission.User;
import org.xsnake.web.common.ActionUtils;
import org.xsnake.web.common.EventUtils;
import org.xsnake.web.session.SessionManager;

import com.alibaba.fastjson.JSON;


public class LoggerInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try{
			User user = SessionManager.getLoginUser();
			LogOperate logOperate = ((HandlerMethod)handler).getMethod().getAnnotation(LogOperate.class);
			if(user!=null && logOperate!=null){
				LoggerEvent log = new LoggerEvent("Logger");
				PageCondition condition = new PageCondition();
				ActionUtils.requestToCondition(condition, request);
				log.setArgsJson(JSON.toJSONString(condition));
				log.setDate(new Date());
				log.setIp(request.getRemoteHost());
				log.setMoudle(logOperate.module());
				log.setNotes(logOperate.notes());
				log.setUrl(request.getRequestURL()  + (request.getQueryString() == null ? "" : "?"+request.getQueryString()));
				log.setUserId(user.getId());
				log.setUsername(user.getName());
				log.setPositionId(user.getPosition().getId());
				log.setPositionName(user.getPosition().getName());
				log.setOrgId(user.getOrg().getId());
				log.setOrgName(user.getOrg().getName());
				EventUtils.publishEvent(log);
			}
		}catch(Exception e){
			//do nothing
		}
		return super.preHandle(request, response, handler);
	}
	
}
