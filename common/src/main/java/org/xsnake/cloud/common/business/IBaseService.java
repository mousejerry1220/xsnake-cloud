package org.xsnake.cloud.common.business;

import org.xsnake.cloud.common.form.BaseForm;

public interface IBaseService {
	
	void recordSave(String id,String businessType,BaseForm form);
	
	int recordUpdate(String id,String businessType,BaseForm form);
	
	int recordDelete(String id,String businessType,BaseForm form);
	
	int recordRenew(String id,String businessType,BaseForm form);

}
