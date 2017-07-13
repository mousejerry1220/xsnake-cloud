package org.xsnake.cloud.service.lov.api.entity;

import org.xsnake.cloud.common.search.BaseCondition;

/**
 * 查询条件
 * @author Administrator
 *
 */
public class LovGroupCondition extends BaseCondition{
	
	private static final long serialVersionUID = 1L;
	
	String systemFlag;
	
	String treeFlag;
	
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
