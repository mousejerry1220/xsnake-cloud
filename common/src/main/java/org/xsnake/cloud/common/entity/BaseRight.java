package org.xsnake.cloud.common.entity;

/**
 * 带有业务数据权限的数据使用的基类
 * @author Administrator
 *
 */
public class BaseRight extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	protected String ownerOrgId;
	
	protected String ownerPositionId;
	
	protected String ownerEmployeeId;
	
	protected String ownerOrgName;
	
	protected String ownerPositionName;
	
	protected String ownerEmployeeName;
	
	public String getOwnerOrgId() {
		return ownerOrgId;
	}

	public void setOwnerOrgId(String ownerOrgId) {
		this.ownerOrgId = ownerOrgId;
	}

	public String getOwnerPositionId() {
		return ownerPositionId;
	}

	public void setOwnerPositionId(String ownerPositionId) {
		this.ownerPositionId = ownerPositionId;
	}

	public String getOwnerEmployeeId() {
		return ownerEmployeeId;
	}

	public void setOwnerEmployeeId(String ownerEmployeeId) {
		this.ownerEmployeeId = ownerEmployeeId;
	}

	public String getOwnerOrgName() {
		return ownerOrgName;
	}

	public void setOwnerOrgName(String ownerOrgName) {
		this.ownerOrgName = ownerOrgName;
	}

	public String getOwnerPositionName() {
		return ownerPositionName;
	}

	public void setOwnerPositionName(String ownerPositionName) {
		this.ownerPositionName = ownerPositionName;
	}

	public String getOwnerEmployeeName() {
		return ownerEmployeeName;
	}

	public void setOwnerEmployeeName(String ownerEmployeeName) {
		this.ownerEmployeeName = ownerEmployeeName;
	}
	
	
}
