package org.xsnake.cloud.common.utils;

import java.util.UUID;

import org.xsnake.cloud.common.exception.BusinessException;

public class StringUtils extends org.springframework.util.StringUtils{

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void validateName(String tableName,String fieldName) {
		char start = tableName.toUpperCase().charAt(0);
		if(start < 'A' || start > 'Z'){
			throw new BusinessException(fieldName+"首字母必须为字母");
		}
		
		if(tableName.trim().indexOf(" ") > -1){
			throw new BusinessException(fieldName+"不能出现空格");
		}
		
		String str = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		for(char c : tableName.toUpperCase().toCharArray()){
			if(str.indexOf(c) < 0){
				throw new BusinessException(fieldName+"只能以下划线，字母，数字组成");
			}
		}
	}
}
