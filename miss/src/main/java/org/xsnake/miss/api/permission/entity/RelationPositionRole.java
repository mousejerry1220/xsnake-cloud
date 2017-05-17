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
* @version 创建时间：2017年5月19日 上午11:01:03 
* 
*/
@Entity
@Table(name = "SYS_PERMISSION_RELATION_POSITION_ROLE")
public class RelationPositionRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_PERMISSION_RELATION_POSITION_ROLE_ID_GENERATOR")
	@GenericGenerator(name="SYS_PERMISSION_RELATION_POSITION_ROLE_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="POSITION_ID")
	private String positionId;
	
	@Column(name="ROLE_ID")
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
