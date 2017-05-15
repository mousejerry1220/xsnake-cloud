package org.xsnake.miss.api.lov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_MANY_TO_MANY_DEFINITION")
public class ManyToManyDefinition implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_MANY_TO_MANY_DEFINITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_MANY_TO_MANY_DEFINITION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="LEFT_GROUP_ID")
	private String leftGroupId;
	
	@Column(name="RIGHT_GROUP_ID")
	private String rightGroupId;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLeftGroupId() {
		return leftGroupId;
	}

	public void setLeftGroupId(String leftGroupId) {
		this.leftGroupId = leftGroupId;
	}

	public String getRightGroupId() {
		return rightGroupId;
	}

	public void setRightGroupId(String rightGroupId) {
		this.rightGroupId = rightGroupId;
	}
	
}
