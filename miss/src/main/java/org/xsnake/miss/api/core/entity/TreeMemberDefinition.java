package org.xsnake.miss.api.lov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_TREE_MEMBER_DEFINITION")
public class TreeMemberDefinition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_TREE_MEMBER_DEFINITION_ID_GENERATOR")
	@GenericGenerator(name="SYS_TREE_MEMBER_DEFINITION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="TREE_DEFINITION_ID")
	private String treeDefinitionId;
	
	@Column(name="ENTITY_DEFINITION_ID")//参与这个树的实体
	private String entityDefinitionId;
	
	@Column(name="PARENT_ENTITY_DEFINITION_ID")
	private String parentEntityDefinitionId;//接受成为上级的实体
	
	@Column(name="LEAF_FLAG")
	private String leafFlag;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTreeDefinitionId() {
		return treeDefinitionId;
	}

	public void setTreeDefinitionId(String treeDefinitionId) {
		this.treeDefinitionId = treeDefinitionId;
	}

	public String getParentEntityDefinitionId() {
		return parentEntityDefinitionId;
	}

	public void setParentEntityDefinitionId(String parentEntityDefinitionId) {
		this.parentEntityDefinitionId = parentEntityDefinitionId;
	}

	public String getEntityDefinitionId() {
		return entityDefinitionId;
	}

	public void setEntityDefinitionId(String entityDefinitionId) {
		this.entityDefinitionId = entityDefinitionId;
	}

	public String getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(String leafFlag) {
		this.leafFlag = leafFlag;
	}
	
}
