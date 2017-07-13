package org.xsnake.cloud.service.lov.api.entity;

import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 * input form 
 * @author Administrator
 *
 */

public class TreeForm {

	@NotNull(message=" code must not be null !")
	String code;
	
	@NotNull(message=" name must not be null !")
	String name;
	
	String systemFlag = "N";
	
	String remark;
	
	Set<String> nodeSources;

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

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public Set<String> getNodeSources() {
		return nodeSources;
	}

	public void setNodeSources(Set<String> nodeSources) {
		this.nodeSources = nodeSources;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
