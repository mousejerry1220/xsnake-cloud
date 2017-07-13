package org.xsnake.cloud.service.lov.api.entity;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.xsnake.cloud.common.form.BaseForm;

public class LovMemberForm extends BaseForm {

	private static final long serialVersionUID = 1L;

	String id;
	
	@NotNull(message = "groupCode must not be null")
	String groupCode;

	@NotNull(message = "code must not be null")
	String code;

	@NotNull(message = "name must not be null")
	String name;

	String remark;
	
	String searchKey;
	
	/**
	 * 节点在树中的位置,key为所在的树，value为父节点 tree1 : parent1 tree2 : parent2
	 */
	Map<String, String> treeParentMap;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

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

	public Map<String, String> getTreeParentMap() {
		return treeParentMap;
	}

	public void setTreeParentMap(Map<String, String> treeParentMap) {
		this.treeParentMap = treeParentMap;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
