package org.xsnake.miss.api.lov.entity;

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
@Table(name = "SYS_ENTITY_DEFINITION")
public class EntityDefinition implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_ENTITY_DEFINITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_ENTITY_DEFINITION_ID_GENERATOR", strategy="assigned")
	@Column(name="ROW_ID",length=50,nullable = false)
	private String id;
	
	@Column(name="NAME",length=50,nullable = false)
	private String name;
	
	@Column(name="REMARK",length=500)
	private String remark;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="SYSTEM_FLAG")
	private String systemFlag;
	
	@Column(name="SORT_NO")
	private Integer sortNo;
	
	@Column(name="TREE_FLAG")
	private String treeFlag;//如果标示为树，则生成单个实体的树
	
	public void validate(){
		if(StringUtils.isEmpty(name)){
			throw new MissException("1030");
		}
		if(name.length() > 50){
			throw new MissException("1031");
		}
		if(remark!=null && remark.length() > 500){
			throw new MissException("1032");
		}
		if(StringUtils.isEmpty(id)){
			throw new MissException("1033");
		}
		if(id.length() > 50){
			throw new MissException("1034");
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getTreeFlag() {
		return treeFlag;
	}

	public void setTreeFlag(String treeFlag) {
		this.treeFlag = treeFlag;
	}
	
}
