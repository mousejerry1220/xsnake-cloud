package org.xsnake.cloud.service.lov.api.entity;

import javax.validation.constraints.NotNull;

import org.xsnake.cloud.common.form.BaseForm;

public class LovGroupForm extends BaseForm{

	private static final long serialVersionUID = 1L;

	@NotNull(message=" code must not be null !")
	String code;
	
	@NotNull(message=" name must not be null !")
	String name;
	
	String remark;
	
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

	public String getTreeFlag() {
		return treeFlag;
	}

	public void setTreeFlag(String treeFlag) {
		this.treeFlag = treeFlag;
	}
	
}
