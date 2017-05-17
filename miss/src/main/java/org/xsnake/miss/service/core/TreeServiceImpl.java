package org.xsnake.miss.service.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xsnake.dao.BaseDao;
import org.xsnake.miss.api.core.ITreeService;
import org.xsnake.miss.api.core.entity.TreeDefinition;
import org.xsnake.miss.api.core.entity.TreeMemberDefinition;
import org.xsnake.miss.api.core.entity.TreeNode;
import org.xsnake.miss.api.core.model.TreeDefinitionModel;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月17日 下午11:14:29 
* 
*/
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class TreeServiceImpl implements ITreeService{

	@Autowired
	BaseDao baseDao;
	
	public void move(String treeId, String nodeId, String newParentId) {
		
	}

	public List<TreeNode> allTree(String treeDefinitionId){
		StringBuffer hql = new StringBuffer(" from TreeNode nd where nd.treeDefinitionId = ? order by nd.sortNo ");
		List<Object> args = new ArrayList<Object>();
		args.add(treeDefinitionId);
		return baseDao.findEntity(hql.toString(),args.toArray());
	}
	
	public List<TreeNode> allChildren(String treeDefinitionId, String parentId) {
		StringBuffer hql = new StringBuffer(" from TreeNode nd where nd.treeDefinitionId = ? and nd.idPath like ? order by nd.level , nd.sortNo ");
		List<Object> args = new ArrayList<Object>();
		args.add(treeDefinitionId);
		args.add("%"+parentId+"%");
		return baseDao.findEntity(hql.toString(),args.toArray());
	}
	
	public List<TreeNode> children(String treeDefinitionId, String parentId) {
		StringBuffer hql = new StringBuffer(" from TreeNode nd where nd.treeDefinitionId = ? and nd.parentId = ? order by nd.sortNo ");
		List<Object> args = new ArrayList<Object>();
		args.add(treeDefinitionId);
		args.add(parentId);
		return baseDao.findEntity(hql.toString(),args.toArray());
	}

	public void definition(TreeDefinitionModel treeDefinitionModel) {
		TreeDefinition treeDefinition = treeDefinitionModel.getTreeDefinition();
		treeDefinition.validate();
		baseDao.save(treeDefinition);
		List<TreeMemberDefinition> treeMemberDefinitionList = treeDefinitionModel.getTreeMemberDefinitionList();
		for(TreeMemberDefinition treeMemberDefinition : treeMemberDefinitionList){
			treeMemberDefinition.validate();
			treeMemberDefinition.setTreeDefinitionId(treeDefinition.getId());
			baseDao.save(treeMemberDefinition);
		}
	}

}
