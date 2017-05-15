package org.xsnake.web;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonResult {

	public static final String TYPE_SUCCESS = "success";
	public static final String TYPE_ERROR = "error";
	
	public static String toSuccessJson(){
		return JSON.toJSONString(createResultMap(TYPE_SUCCESS,null));
	}
	
	public static String toSuccessJson(String message){
		Map<String,Object> map = createResultMap(TYPE_SUCCESS,message);
		return JSON.toJSONString(map);
	}
	
	public static String toSuccessJson(Object object){
		Map<String,Object> map = createResultMap(TYPE_SUCCESS,object);
		return JSON.toJSONString(map);
	}
	
	private static Map<String,Object> createResultMap(String type,Object result){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status",type);
		map.put("message",result);
		return map;
	}
	
	public static String toErrorJson(String message){
		Map<String,Object> map = createResultMap(TYPE_ERROR,message);
		return JSON.toJSONString(map);
	}
	
	public static String toErrorJson(Object message){
		Map<String,Object> map = createResultMap(TYPE_ERROR,message);
		return JSON.toJSONString(map);
	}
	
	public static <T> T toObject(String json,Class<T> cls){
		return JSON.parseObject(json, cls);
	}
	
}
