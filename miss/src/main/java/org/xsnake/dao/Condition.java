package org.xsnake.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Condition implements Serializable{

	private static final long serialVersionUID = 1L;

	protected Map<String, Object> conditionMap = new HashMap<String, Object>();

	public Object get(String key){
		return conditionMap.get(key);
	}
	
	public void set(String key,Object obj){
		conditionMap.put(key, obj);
	}
	
}
