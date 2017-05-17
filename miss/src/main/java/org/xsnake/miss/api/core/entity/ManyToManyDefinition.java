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
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="LEFT_ENTITY_DEFINITION_ID")
	private String leftEntityDefinitionId;
	
	@Column(name="RIGHT_ENTITY_DEFINITION_ID")
	private String rightEntityDefinitionId;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getLeftEntityDefinitionId() {
		return leftEntityDefinitionId;
	}

	public void setLeftEntityDefinitionId(String leftEntityDefinitionId) {
		this.leftEntityDefinitionId = leftEntityDefinitionId;
	}

	public String getRightEntityDefinitionId() {
		return rightEntityDefinitionId;
	}

	public void setRightEntityDefinitionId(String rightEntityDefinitionId) {
		this.rightEntityDefinitionId = rightEntityDefinitionId;
	}
	
}
