package org.xsnake.cloud.service.lov.api.entity;

import org.xsnake.cloud.common.entity.BaseInfo;

public class LovGroup extends BaseInfo{
	
	private static final long serialVersionUID = 1L;

	String code;
	
	String name;
	
	String remark;
	
	String systemFlag;
	
	String treeFlag;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public String getTreeFlag() {
		return treeFlag;
	}

	public void setTreeFlag(String treeFlag) {
		this.treeFlag = treeFlag;
	}

}
