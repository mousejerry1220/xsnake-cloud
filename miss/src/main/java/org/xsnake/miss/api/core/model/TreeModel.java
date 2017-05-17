package org.xsnake.miss.api.lov.model;

import java.io.Serializable;
import java.util.List;

import org.xsnake.miss.api.lov.entity.TreeDefinition;
import org.xsnake.miss.api.lov.entity.TreeMemberDefinition;

public class TreeModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private TreeDefinition treeDefinition;
	
	private List<TreeMemberDefinition> treeMemberDefinitionList;

	public TreeDefinition getTreeDefinition() {
		return treeDefinition;
	}

	public void setTreeDefinition(TreeDefinition treeDefinition) {
		this.treeDefinition = treeDefinition;
	}

	public List<TreeMemberDefinition> getTreeMemberDefinitionList() {
		return treeMemberDefinitionList;
	}

	public void setTreeMemberDefinitionList(List<TreeMemberDefinition> treeMemberDefinitionList) {
		this.treeMemberDefinitionList = treeMemberDefinitionList;
	}
	
}
