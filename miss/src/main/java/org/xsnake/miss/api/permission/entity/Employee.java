package org.xsnake.miss.api.permission.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月19日 上午12:13:42 
* 
*/
@Entity
@Table(name = "SYS_PERMISSION_EMPLOYEE")
public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_PERMISSION_EMPLOYEE_ID_GENERATOR")
	@GenericGenerator(name="SYS_PERMISSION_EMPLOYEE_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="LAST_POSITION_ID")
	private String lastPositionId;
	
	@Column(name="MAIN_POSITION_ID")
	private String mainPositionId;
	
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="END_DATE")
	private Date endDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastPositionId() {
		return lastPositionId;
	}

	public void setLastPositionId(String lastPositionId) {
		this.lastPositionId = lastPositionId;
	}

	public String getMainPositionId() {
		return mainPositionId;
	}

	public void setMainPositionId(String mainPositionId) {
		this.mainPositionId = mainPositionId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
