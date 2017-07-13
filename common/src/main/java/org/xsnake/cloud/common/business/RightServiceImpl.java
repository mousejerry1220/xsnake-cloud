package org.xsnake.cloud.common.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xsnake.cloud.common.dao.DaoTemplate;
import org.xsnake.cloud.common.exception.BugException;
import org.xsnake.cloud.common.form.BaseForm;

@Service
public class RightServiceImpl implements IRightService{

	@Autowired
	DaoTemplate daoTemplate;
	
	@Override
	public void recordOwner(String id, String businessType, BaseForm form) {
		Assert.notNull(id);
		Assert.notNull(businessType);
		form.validate();
		String sql = "insert into t_sys_common_right(id,ownerEmployeeId,ownerPositionId,ownerOrgId,businessType) values ( ?, ?, ?, ?, ? )";
		try{
			daoTemplate.execute(sql,new Object[]{
				id,form.getOperatorEmployeeId(),form.getOperatorPositionId(),form.getOperatorOrgId(),businessType
			});
		}catch (Exception e) {
			throw new BugException(e);
		}
	}

	public int changeOwner(String id, String businessType, String ownerEmployeeId, String ownerPositionId, String ownerOrgId) {
		Assert.notNull(id);
		Assert.notNull(businessType);
		Assert.notNull(ownerEmployeeId);
		Assert.notNull(ownerPositionId);
		Assert.notNull(ownerOrgId);
		String sql = " update t_sys_common_right set ownerEmployeeId = ? ,ownerPositionId = ? ,ownerOrgId = ?  where id = ? and businessType = ? ";
		return daoTemplate.execute(sql,new Object[]{
			ownerEmployeeId,ownerPositionId,ownerOrgId,id,businessType
		});
	}


}
