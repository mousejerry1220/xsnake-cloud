package org.xsnake.cloud.common.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

public class BaseAction implements AjaxSupport{

	private String getPackagePath() {
		String className = this.getClass().getPackage().getName();
		String packagePath = className.replaceAll("\\.", "/");
		return  "/" + packagePath + "/";
	}

	protected String forward(String path) {
		return getPackagePath() + path;
	}

	@Override
	public String sendSuccessMessage() {
		return sendSuccessMessage(null);
	}

	@Override
	public String sendSuccessMessage(Object result) {
		return sendMessage(toJson(createResultMap("success",result)));
	}

	@Override
	public String sendErrorMessage() {
		return sendErrorMessage(null);
	}

	@Override
	public String sendErrorMessage(Object result) {
		return sendMessage(toJson(createResultMap("error",result)));
	}
	
	public static Map<String,Object> createResultMap(String type,Object result){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status",type);
		map.put("message",result);
		return map;
	}
	
	public static String toJson(Object jsonObject){
		String result = JSON.toJSONString(jsonObject);
		result = result.replaceAll("\t","");
		return result;
	}

	public static String sendMessage(String type,String result){
		return sendMessage(toJson(createResultMap(type,result)));
	}
	
	private static String sendMessage(String result){
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		ServletOutputStream witer = null;
		try {
			witer = response.getOutputStream();
			witer.write(result.getBytes());
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
