package org.xsnake.miss.api.lov;

import java.util.List;

import org.xsnake.miss.api.lov.entity.TreeNode;
import org.xsnake.miss.api.lov.model.TreeModel;

public interface ITreeService {
	
	void move(String treeId,String nodeId,String newParentId);
	
	List<TreeNode> children(String treeId,String nodeId);
	
	void definition(TreeModel treeModel);
	
}
