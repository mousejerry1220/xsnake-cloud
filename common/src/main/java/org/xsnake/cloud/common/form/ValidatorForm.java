package org.xsnake.cloud.common.form;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.xsnake.cloud.common.exception.BusinessException;

public abstract class ValidatorForm implements Serializable{

	private static final long serialVersionUID = 1L;

	public void validate(){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ValidatorForm>> errors = validator.validate(this);
		if(errors.size() > 0){
			StringBuffer message = new StringBuffer();
			for(ConstraintViolation<ValidatorForm> error : errors ){
				message.append(error.getMessage()).append(";");
			}
			throw new BusinessException(message.toString());
		}
	}
	
}
