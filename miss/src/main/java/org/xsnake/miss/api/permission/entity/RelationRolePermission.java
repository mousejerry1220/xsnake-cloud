package org.xsnake.miss.api.permission.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月19日 上午11:02:34 
* 
*/
@Entity
@Table(name = "SYS_PERMISSION_RELATION_ROLE_PERMISSION")
public class RelationRolePermission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_PERMISSION_RELATION_ROLE_PERMISSION_ID_GENERATOR")
	@GenericGenerator(name="SYS_PERMISSION_RELATION_ROLE_PERMISSION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="ROLE_ID")
	private String roleId;
	
	@Column(name="PERMISSION_ID")
	private String permissionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	
}
