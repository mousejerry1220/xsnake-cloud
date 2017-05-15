package org.xsnake.miss.api.lov;

import java.util.List;

import org.xsnake.miss.api.lov.entity.LovMember;
import org.xsnake.miss.api.lov.model.TreeModel;

public interface ILovTreeService {
	
	void move(String treeId,String nodeId,String newParentId);
	
	List<LovMember> children(String treeId,String nodeId);
	
	void definition(TreeModel treeModel);
	
}
