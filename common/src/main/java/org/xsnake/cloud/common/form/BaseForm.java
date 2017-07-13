package org.xsnake.cloud.common.form;

import javax.validation.constraints.NotNull;

public class BaseForm extends ValidatorForm{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="操作人不能为空")
	protected String operatorEmployeeId;
	
	@NotNull(message="操作人的岗位不能为空")
	protected String operatorPositionId;
	
	@NotNull(message="操作人的组织不能为空")
	protected String operatorOrgId;

	public String getOperatorEmployeeId() {
		return operatorEmployeeId;
	}

	public void setOperatorEmployeeId(String operatorEmployeeId) {
		this.operatorEmployeeId = operatorEmployeeId;
	}

	public String getOperatorPositionId() {
		return operatorPositionId;
	}

	public void setOperatorPositionId(String operatorPositionId) {
		this.operatorPositionId = operatorPositionId;
	}

	public String getOperatorOrgId() {
		return operatorOrgId;
	}

	public void setOperatorOrgId(String operatorOrgId) {
		this.operatorOrgId = operatorOrgId;
	}
	
}
