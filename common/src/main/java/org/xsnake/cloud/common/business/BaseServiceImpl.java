package org.xsnake.cloud.common.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xsnake.cloud.common.dao.DaoTemplate;
import org.xsnake.cloud.common.exception.BugException;
import org.xsnake.cloud.common.form.BaseForm;

@Service
public class BaseServiceImpl implements IBaseService{

	@Autowired
	DaoTemplate daoTemplate;
	
	/**
	 * 统一记录所有的数据的操作人与操作时间。在创建数据时，主键为id,各个业务创建时必须使用UUID避免id重复.
	 */
	@Override
	public void recordSave(String id, String businessType, BaseForm form) {
		String sql = "insert into t_sys_common_info(id,createBy,createDate,lastUpdateBy,lastUpdateDate,businessType) values ( ?, ?, ?, ?, ?, ? )";
		try{
			Date now = new Date();
			daoTemplate.execute(sql,new Object[]{
				id,form.getOperatorEmployeeId(),now,form.getOperatorEmployeeId(),now,businessType
			});
		}catch (Exception e) {
			throw new BugException(e);
		}
		
	}

	/**
	 * 更新修改人信息
	 */
	@Override
	public int recordUpdate(String id, String businessType, BaseForm form) {
		String sql = " update t_sys_common_info set lastUpdateBy = ? , lastUpdateDate = ? where id = ? and businessType = ? ";
		return daoTemplate.execute(sql,new Object[]{
			form.getOperatorEmployeeId(),new Date(),id,businessType
		});
	}

	/**
	 * 删除数据，记录所有被删除了的业务数据。如果已经存在了
	 */
	@Override
	public int recordDelete(String id, String businessType, BaseForm form) {
		String sql = " insert into t_sys_common_delete (id,deleteFlag,deleteDate,deleteBy,businessType) values ( ?, ?, ?, ?, ?) ";
		try{
			return daoTemplate.execute(sql,new Object[]{
				id,"Y",new Date(),form.getOperatorEmployeeId(),businessType
			});
		}catch (Exception e) {
			sql = " update t_sys_common_delete set deleteFlag = ? , deleteDate = ? , deleteBy = ? where id = ? and businessType = ? ";
			return daoTemplate.execute(sql,new Object[]{
				"Y", new Date(), form.getOperatorEmployeeId(), id, businessType
			});
		}
	}

	@Override
	public int recordRenew(String id, String businessType, BaseForm form) {
		String sql = " update t_sys_common_delete set deleteFlag = ? , deleteDate = ? , deleteBy = ? where id = ? and businessType = ? ";
		return daoTemplate.execute(sql,new Object[]{
			"N", new Date(), form.getOperatorEmployeeId(), id, businessType
		});
	}

}
