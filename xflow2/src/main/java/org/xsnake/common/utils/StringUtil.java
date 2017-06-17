package org.xsnake.common.utils;

import java.util.Map;
import java.util.UUID;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.JSON;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;


public class StringUtil extends StringUtils{
	
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid.toUpperCase();
	}
	
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static String getTemplateString(String sql,Map<String,String> map) throws Exception{
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		StringTemplateLoader loader = new StringTemplateLoader();
		loader.putTemplate("template", sql);
		Configuration c;
		try {
			c = config.createConfiguration();
			c.setTemplateLoader(loader);
			String result = FreeMarkerTemplateUtils.processTemplateIntoString(c.getTemplate("template"), map);
			return result;
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
