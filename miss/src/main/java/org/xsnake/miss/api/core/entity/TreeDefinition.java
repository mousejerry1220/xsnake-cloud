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
@Table(name = "SYS_TREE_DEFINITION")
public class TreeDefinition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_TREE_DEFINITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_TREE_DEFINITION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="NAME",nullable=false)
	private String name;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="single_FLAG",nullable=false)
	private String singleFlag;//是否单个实体组成的树

	public void validate(){
		if(StringUtils.isEmpty(name)){
			throw new MissException("1040");
		}
		if(StringUtils.isEmpty(singleFlag)){
			throw new MissException("1041");
		}
	}
	
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

	public String getSingleFlag() {
		return singleFlag;
	}

	public void setSingleFlag(String singleFlag) {
		this.singleFlag = singleFlag;
	}
	
}
