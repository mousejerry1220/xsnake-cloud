package org.xsnake.cloud.service.lov.api.entity;

import org.xsnake.cloud.common.search.BaseCondition;

public class LovMemberCondition extends BaseCondition{

	private static final long serialVersionUID = 1L;

	private String groupCode;
	
	private String parentId;
	
	private int level;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
