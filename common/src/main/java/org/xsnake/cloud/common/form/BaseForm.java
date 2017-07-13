package org.xsnake.cloud.common.form;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

public class BaseForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="operatorEmployeeId must not be null")
	protected String operatorEmployeeId;
	
	@NotNull(message="operatorPositionId must not be null")
	protected String operatorPositionId;
	
	@NotNull(message="operatorOrgId must not be null")
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
	
	public void validate(){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<BaseForm>> errors = validator.validate(this);
		if(errors.size() > 0){
			StringBuffer message = new StringBuffer();
			for(ConstraintViolation<BaseForm> error : errors ){
				message.append(error.getMessage()).append(";");
			}
			throw new ValidationException(message.toString());
		}
	}
	
}
