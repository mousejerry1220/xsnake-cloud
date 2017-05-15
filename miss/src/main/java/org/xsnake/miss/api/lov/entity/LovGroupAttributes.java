package org.xsnake.miss.api.lov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_LOV_GROUP_ATTRIBUTE")
public class LovGroupAttributes implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_LOV_GROUP_ATTRIBUTE_ID_GENERATOR")
	@GenericGenerator(name="SYS_LOV_GROUP_ATTRIBUTE_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="FIELD_CODE")
	private String fieldCode; //同一个定义下，只能出现一次 
	
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="LENGTH")
	private Integer length;
	
	@Column(name="SORT_NO")
	private Integer sortNo;
	
	@Column(name="TYPE")
	private String type; //类型，字符串，数字，小数等
	
	@Column(name="GROUP_ID",length = 32)
	private String groupId;//归属哪个定义
	
	@Column(name="RELATION_GROUP_ID",length = 32)
	private String relationGroupId;

	@Column(name = "NULL_FLAG", length = 1, nullable = false)
	private String nullFlag = "Y";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRelationGroupId() {
		return relationGroupId;
	}

	public void setRelationGroupId(String relationGroupId) {
		this.relationGroupId = relationGroupId;
	}

	public String getNullFlag() {
		return nullFlag;
	}

	public void setNullFlag(String nullFlag) {
		this.nullFlag = nullFlag;
	}
	
}
