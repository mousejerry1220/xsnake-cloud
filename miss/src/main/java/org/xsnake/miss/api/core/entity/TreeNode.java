package org.xsnake.miss.api.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_TREE_NODE")
public class TreeNode implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_TREE_NODE_ID_GENERATOR")
	@GenericGenerator(name="SYS_TREE_NODE_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;

	@Column(name="NODE_NAME")
	private String nodeName;
	
	@Column(name="NODE_CODE")
	private String nodeCode;
	
	@Column(name="NODE_ID")
	private String nodeId;
	
	@Column(name="NODE_ENTITY_ID")
	private String nodeEntityId;
	
	@Column(name="TREE_DEFINITION_ID")
	private String treeDefinitionId;
	
	@Column(name="PARENT_ID")
	private String parentId;
	
	@Column(name="PARENT_ENTITY_ID")
	private String parentEntityId;
	
	@Column(name="SORT_NO")
	private Integer sortNo;
	
	@Column(name="LEAF_FLAG")
	private String leafFlag;
	
	@Column(name="LEVEL")
	private Integer level;
	
	@Column(name="NAME_PATH")
	private String namePath;
	
	@Column(name="CODE_PATH")
	private String codePath;
	
	@Column(name="ID_PATH")
	private String idPath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(String leafFlag) {
		this.leafFlag = leafFlag;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public String getNodeEntityId() {
		return nodeEntityId;
	}

	public void setNodeEntityId(String nodeEntityId) {
		this.nodeEntityId = nodeEntityId;
	}

	public String getTreeDefinitionId() {
		return treeDefinitionId;
	}

	public void setTreeDefinitionId(String treeDefinitionId) {
		this.treeDefinitionId = treeDefinitionId;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	
}
