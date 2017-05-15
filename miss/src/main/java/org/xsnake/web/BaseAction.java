package org.xsnake.web;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xsnake.web.session.SessionManager;

public abstract class BaseAction extends SessionManager implements AjaxSupport{

	public static final String SUFFIX = ".html";
	
	@InitBinder
	protected void initBaseBinder(WebDataBinder binder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,true)); 
	}
	
	private String getPackagePath() {
		String className = this.getClass().getPackage().getName();
		String packagePath = className.replaceAll("\\.", "/");
		return  "/WEB-INF/classes/" + packagePath + "/";
	}
	
	protected String forward(String path) {
		return getPackagePath() + path;
	}
	
	protected String redirect(String path){
		if(path.indexOf(SUFFIX) > -1){
			return "redirect:" + path;
		}
		return "redirect:" + path + SUFFIX;
	}
	
	public String sendSuccessMessage(Object result){
		return sendMessage(JsonResult.toSuccessJson(result));
	}
	
	public String sendErrorMessage(Object result){
		return sendMessage(JsonResult.toErrorJson(result));
	}
	
	public String sendSuccessMessage(){
		return sendMessage(JsonResult.toSuccessJson(null));
	}
	
	public String sendErrorMessage(){
		return sendMessage(JsonResult.toErrorJson(null));
	}
	
	private String sendMessage(String result){
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		Writer witer = null;
		try {
			witer = response.getWriter();
			witer.write(result);
			witer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(witer !=null){ 
				try {
					witer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
