package org.xsnake.miss.api.lov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_LOV_GROUP")
public class LovGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_LOV_GROUP_ID_GENERATOR")
	@GenericGenerator(name="SYS_LOV_GROUP_ID_GENERATOR", strategy="assigned")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="SYSTEM_FLAG")
	private String systemFlag;
	
	@Column(name="TREE_FLAG")
	private String treeFlag;

	@Column(name="TABLE_FLAG")
	private String tableFlag;
	
	@Column(name="SORT_NO")
	private Integer sortNo;
	
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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getTableFlag() {
		return tableFlag;
	}

	public void setTableFlag(String tableFlag) {
		this.tableFlag = tableFlag;
	}
	
}
