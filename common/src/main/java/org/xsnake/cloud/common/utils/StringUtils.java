package org.xsnake.cloud.common.utils;

import java.util.UUID;

public class StringUtils extends org.springframework.util.StringUtils{

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
