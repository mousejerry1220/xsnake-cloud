package org.xsnake.miss.api.lov.model;

import java.io.Serializable;
import java.util.List;

import org.xsnake.miss.api.lov.entity.Tree;
import org.xsnake.miss.api.lov.entity.TreeDefinition;

public class TreeModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Tree tree;
	
	private List<TreeDefinition> treeDefinitionList;

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public List<TreeDefinition> getTreeDefinitionList() {
		return treeDefinitionList;
	}

	public void setTreeDefinitionList(List<TreeDefinition> treeDefinitionList) {
		this.treeDefinitionList = treeDefinitionList;
	}
	
}
