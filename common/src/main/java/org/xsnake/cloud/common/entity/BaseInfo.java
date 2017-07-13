package org.xsnake.cloud.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务数据基类，返回业务的创建，编辑信息
 * @author Administrator
 *
 */
public class BaseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建修改信息
	 */
	protected String createBy;
	
	protected String createByName;
	
	protected Date createDate;
	
	protected String lastUpdateBy;
	
	protected String lastUpdateByName;
	
	protected Date lastUpdateDate;
	
	protected String deleteFlag;
	
	protected String deleteBy;
	
	protected Date deleteDate;
	
	protected String deleteByName;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getLastUpdateByName() {
		return lastUpdateByName;
	}

	public void setLastUpdateByName(String lastUpdateByName) {
		this.lastUpdateByName = lastUpdateByName;
	}

	
}
