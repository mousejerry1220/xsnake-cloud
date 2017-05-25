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
* @version 创建时间：2017年5月19日 上午9:59:16 
* 
*/
@Entity
@Table(name = "SYS_PERMISSION_RELATION_EMPLOYEE_POSITION")
public class RelationEmployeePosition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_PERMISSION_RELATION_EMPLOYEE_POSITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_PERMISSION_RELATION_EMPLOYEE_POSITION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name="POSITION_ID")
	private String positionId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
}
