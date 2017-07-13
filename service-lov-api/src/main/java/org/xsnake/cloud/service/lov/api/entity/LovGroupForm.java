package org.xsnake.cloud.service.lov.api.entity;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;
import org.xsnake.cloud.common.exception.BusinessException;
import org.xsnake.cloud.common.form.BaseForm;
import org.xsnake.cloud.common.utils.StringUtils;

public class LovGroupForm extends BaseForm{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message="代码不能为空")
	@Max(100)
	String code;//varchar(100)只有当expandFlag = Y && headerFlag = Y 则可以配置事件，状态
	
	@NotEmpty(message="名称不能为空")
	@Max(100)
	String name;//varchar(100)
	
	@Max(4000)
	String remark = null;//varchar(4000)备注
	
	@Max(1)
	String treeFlag = "N";//varchar(1)
	
	@Max(1)
	String systemFlag = "N";//varchar(1)系统标示，系统必须的或者自定义的
	
	@Max(1)
	String expandFlag = "N";//varchar(1)是否扩展，如果是扩展则主数据在data_business表，否则在lov_memeber表
	
	@Max(1)
	String headerFlag = null;//varchar(1)当扩展数据为Y的时候，生是否主数据
	
	@Max(100)
	String headerGroupCode = null;//varchar(100)当不是主数据时生效，选择关联的主数据，非扩展数据不可当做主数据
	
	@Max(30)
	String tableName = null;//varchar(30)对应的表明
	
	@Max(1)
	String viewFlag = null;//varchar(1)如果是已经存在的视图，则无需自动建表
	
	@Max(1)
	String lockFlag = null;//varchar(1)如果被锁定则不能修改
	
	@Max(1)
	Long version = 1l;//varchar(1)如果为扩展则记录每次版本
	
	@Max(30)
	String permissionType = null;//varchar(30)如果是扩展类型，且为头数据，可以设置权限类型
	
	@Max(100)
	String permissionTreeCode = null;//varchar(100)权限所提供数据支持的树形结构

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTreeFlag() {
		return treeFlag;
	}

	public void setTreeFlag(String treeFlag) {
		this.treeFlag = treeFlag;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public String getExpandFlag() {
		return expandFlag;
	}

	public void setExpandFlag(String expandFlag) {
		this.expandFlag = expandFlag;
	}

	public String getHeaderFlag() {
		return headerFlag;
	}

	public void setHeaderFlag(String headerFlag) {
		this.headerFlag = headerFlag;
	}

	public String getHeaderGroupCode() {
		return headerGroupCode;
	}

	public void setHeaderGroupCode(String headerGroupCode) {
		this.headerGroupCode = headerGroupCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getPermissionTreeCode() {
		return permissionTreeCode;
	}

	public void setPermissionTreeCode(String permissionTreeCode) {
		this.permissionTreeCode = permissionTreeCode;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Override
	public void validate() {
		super.validate();
		if("Y".equals(expandFlag)){
			if("Y".equals(treeFlag)){
				throw new BusinessException("如果是扩展类型，则不能为树形，可以通过映射等其他方式建立关系");
			}
			
			if(StringUtils.isEmpty(headerFlag)){
				throw new BusinessException("扩展类型必须确定它是主数据还是非主数据");
			}
			
			if("N".equals(headerFlag) && StringUtils.isEmpty(headerGroupCode)){
				throw new BusinessException("非主数据必须指定他的归属主数据");
			}
			
			if("Y".equals(viewFlag) && "N".equals(headerFlag)){
				throw new BusinessException("视图类型必须为主数据");
			}
			
			if("Y".equals(viewFlag) && StringUtils.isEmpty(tableName)){
				throw new BusinessException("视图类型必须指定视图名");
			}
			
			if(!StringUtils.isEmpty(tableName)){
				StringUtils.validateName(tableName,"表名或者视图");
			}
			
		}
	}
	
}
