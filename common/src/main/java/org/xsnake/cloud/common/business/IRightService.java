package org.xsnake.cloud.common.business;

import org.xsnake.cloud.common.form.BaseForm;

public interface IRightService {

	/**
	 * 记录数据的拥有者
	 * @param id
	 * @param businessType
	 * @param form
	 */
	void recordOwner(String id,String businessType,BaseForm form);
	
	/**
	 * 
	 * @param id
	 * @param businessType
	 * @param ownerEmployeeId
	 * @param ownerPositionId
	 * @param ownerOrgId
	 * @return
	 */
	int changeOwner(String id, String businessType, String ownerEmployeeId, String ownerPositionId, String ownerOrgId); 
	
}
