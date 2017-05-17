package org.xsnake.miss.api.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;
import org.xsnake.miss.exception.MissException;

@Entity
@Table(name = "SYS_ENTITY_ATTRIBUTES_DEFINITION")
public class EntityAttributesDefinition implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_ENTITY_ATTRIBUTES_DEFINITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_ENTITY_ATTRIBUTES_DEFINITION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="FIELD_CODE",nullable = false)
	private String fieldCode; //同一个定义下，只能出现一次 
	
	@Column(name="FIELD_NAME",nullable = false)
	private String fieldName;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="LENGTH")
	private Integer length;
	
	@Column(name="SORT_NO")
	private Integer sortNo;
	
	@Column(name="TYPE",length=30,nullable = false)
	private String type; //类型，外键，字符串，数字，小数，日期等
	
	@Column(name="ENTITY_DEFINITION_ID",length = 32,nullable = false)
	private String entityDefinitionId;//归属哪个定义
	
	//为外键类型提供
	@Column(name="RELATION_ENTITY_DEFINITION_ID",length = 32)
	private String relationEntityDefinitionId;
	
	@Column(name = "NULL_FLAG", length = 1, nullable = false)
	private String nullFlag = "Y";
	
	@Column(name="DEFAULT_VALUE")
	private String defaultValue;
	
	//为数值类型提供
	@Column(name="precision")
	private Integer precision;
	
	public void validate(){
		if(StringUtils.isEmpty(fieldCode)){
			throw new MissException("1020");
		}
		if(StringUtils.isEmpty(fieldName)){
			throw new MissException("1021");
		}
		if(StringUtils.isEmpty(type)){
			throw new MissException("1022");
		}
		if(StringUtils.isEmpty(nullFlag)){
			throw new MissException("1024");
		}
	}
	
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

	public String getRelationEntityDefinitionId() {
		return relationEntityDefinitionId;
	}

	public void setRelationEntityDefinitionId(String relationEntityDefinitionId) {
		this.relationEntityDefinitionId = relationEntityDefinitionId;
	}

	public String getNullFlag() {
		return nullFlag;
	}

	public void setNullFlag(String nullFlag) {
		this.nullFlag = nullFlag;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getEntityDefinitionId() {
		return entityDefinitionId;
	}

	public void setEntityDefinitionId(String entityDefinitionId) {
		this.entityDefinitionId = entityDefinitionId;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	
}
