package org.xsnake.miss.api.lov.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.xsnake.miss.api.lov.entity.LovMember;

public class LovMemberModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private LovMember lovMember;
	
	private Map<String,String> attributesMap = new HashMap<String, String>();
	
	public LovMemberModel(LovMember lovMember,Map<String,String> attributesMap){
		this.lovMember = lovMember;
		this.attributesMap = attributesMap;
	}

	public LovMember getLovMember() {
		return lovMember;
	}

	public void setLovMember(LovMember lovMember) {
		this.lovMember = lovMember;
	}

	public Map<String, String> getAttributesMap() {
		return attributesMap;
	}

	public void setAttributesMap(Map<String, String> attributesMap) {
		this.attributesMap = attributesMap;
	}
	
	public String get(String key){
		return attributesMap.get(key);
	}
	
}
