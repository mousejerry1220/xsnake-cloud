package org.xsnake.miss.api.core;

import java.util.List;

import org.xsnake.miss.api.core.entity.TreeNode;
import org.xsnake.miss.api.core.model.TreeDefinitionModel;

public interface ITreeService {
	
	void move(String treeId,String nodeId,String newParentId);
	
	List<TreeNode> children(String treeDefinitionId, String parentId);
	
	void definition(TreeDefinitionModel treeDefinitionModel);
	
	
	
}
