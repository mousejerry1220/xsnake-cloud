package org.xsnake.cloud.service.lov.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xsnake.cloud.common.dao.DaoTemplate;
import org.xsnake.cloud.common.exception.BugException;
import org.xsnake.cloud.common.exception.BusinessException;
import org.xsnake.cloud.common.utils.StringUtils;
import org.xsnake.cloud.service.lov.api.ITreeService;
import org.xsnake.cloud.service.lov.api.entity.Tree;
import org.xsnake.cloud.service.lov.api.entity.TreeForm;
import org.xsnake.cloud.service.lov.api.entity.TreeNode;

@Service
@RestController
public class TreeServiceImpl implements ITreeService{
	//拷贝树，还原树（不要忘了遗漏拷贝动作后新增的节点）
	@Autowired
	DaoTemplate daoTemplate;
	
	
	private final String UPDATE_TREE_SQL = " update t_sys_lov_tree set name = ? ,remark = ? where code = ? ";
	
	private final String UPDATE_TREE_NODE_SQL = " update t_sys_lov_tree_node set name = ? , code = ?  where treeCode = ? and id = ? ";
	
	private final String GET_ALL_TREE_DATA_SQL = "select * from v_sys_lov_tree_node where treeCode = ? order by level,sn,code";
	
	private final String GET_CHILDREN_SQL = "select * from v_sys_lov_tree_node where treeCode = ? and parentId = ? order by level, sn, code";
	
	private final String GET_ALL_CHILDREN_SQL = "select * from v_sys_lov_tree_node where treeCode = ? and idPath like ? order by level, sn, code";
	
	private final String GET_TREE_NODE = "select * from t_sys_lov_tree_node where treeCode = ? and id = ? ";
	
	private final String GET_TREE = "select * from t_sys_lov_tree where code = ? ";
	
	@Override
	@RequestMapping(value="/tree/save",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void save(TreeForm treeForm) {
		Assert.notNull(treeForm);
		//验证编码是否已经存在
		String sql = "select count(1) from t_bee_tree where code = ? ";
		Long count = daoTemplate.queryLong(sql,treeForm.getCode());
		if(count > 0){
			throw new BusinessException("树编码已经存在 ：" + treeForm.getCode());
		}
		//插入树定义
		String SAVE_TREE_SQL = "insert into t_bee_tree(code,name,systemFlag,version,remark,fullFlag) values ( ?, ?, ?, ?, ? , ? ) ";
		daoTemplate.execute(SAVE_TREE_SQL, new Object[]{
			treeForm.getCode(),
			treeForm.getName(),
			treeForm.getSystemFlag() == null ? "N" : treeForm.getSystemFlag(),
			1,
			treeForm.getRemark(),
			treeForm.getFullFlag()
		});
		
		//插入树定义的来源和来源中的节点
		for(String nodeSource : treeForm.getNodeSources()){
			String SAVE_TREE_NODE_SOURCE_SQL = "insert into t_bee_tree_node_source(treeCode,nodeSourceCode) values (?,?) ";
			daoTemplate.execute(SAVE_TREE_NODE_SOURCE_SQL,new Object[]{treeForm.getCode(),nodeSource});
			
			String INIT_TREE_SQL = "INSERT INTO t_bee_tree_node (id,NAME,CODE,parentId,treeCode,LEVEL,idPath,namePath,codePath,nodeSourceCode) SELECT m.id,m.name,m.code,NULL,?,1,CONCAT('/',m.id),CONCAT('/',m.name),CONCAT('/',m.code),m.groupCode FROM t_bee_data_simple m LEFT JOIN t_bee_tree_node tn ON m.id = tn.id AND tn.treeCode = ? WHERE tn.id IS NULL AND m.groupCode = ? ";
			daoTemplate.execute(INIT_TREE_SQL,new Object[]{nodeSource,nodeSource,nodeSource});
		}
	}
	
	/**
	 * 将一个节点转移到另一个节点下，更新子孙节点的冗余数据
	 */
	@RequestMapping(value="/tree/move",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int move(
			@RequestParam(name="treeCode") String treeCode,
			@RequestParam(name="nodeId") String nodeId,
			@RequestParam(name="parentId") String parentId,
			@RequestParam(name="version") int version
		){
		Assert.hasLength(treeCode, "treeCode不能为空");
		Assert.hasLength(nodeId, "nodeId不能为空");
		//验证客户端移动树的时候的版本号，是否当前版本
		if(version > 0){
			String versionSql = " select count(1) from t_sys_lov_tree where code = ? and version = ? ";
			Long versionCount = daoTemplate.queryLong(versionSql, new Object[]{treeCode,version});
			if(versionCount == 0){
				throw new BugException("树数据已经升级，获取新版本后再操作");
			}
		}
		//验证客户端移动树的时候是否将子节点设置成为父节点
		if(parentId != null){
			String sql = " select count(1) from t_sys_lov_tree_node where treeCode=? and id = ? and idPath like ? ";
			Long count = daoTemplate.queryLong(sql, new Object[]{treeCode,parentId,"%"+nodeId+"%"});
			if(count>0){
				throw new BugException("不允许将子节点设置为父级");
			}
		}
		//判断参数是否正确
		TreeNode node = getTreeNode(treeCode, nodeId);
		TreeNode parent = getTreeNode(treeCode, parentId);
		if(node == null){
			throw new BugException("参数异常，没有找到对应的移动节点:"+nodeId );
		}
		if(parent == null){
			throw new BugException("参数异常，没有找到对应的父节点:" + parentId);
		}
		updateAllNodeInfo(treeCode, nodeId, parentId);
		int newVersion = upgradeTree(treeCode);
		return newVersion;
	}
	
	//升级树的版本
	private int upgradeTree(String treeCode){
		String sql = " update t_sys_lov_tree set version = version + 1 where code = ? ";
		daoTemplate.execute(sql,new Object[]{treeCode});
		Tree tree = daoTemplate.queryObject(GET_TREE, new Object[]{treeCode}, Tree.class);
		return tree.getVersion();
	}
	
	/**
	 * 递归更新全部子节点的冗余信息
	 * @param treeCode
	 * @param nodeId
	 * @param newParentId
	 */
	private void updateAllNodeInfo(String treeCode,String nodeId,String parentId ){
		updateNodeInfo(treeCode, nodeId, parentId);
		List<TreeNode> children = getChildren(treeCode, nodeId);
		for(TreeNode tn : children){
			updateAllNodeInfo(treeCode, tn.getId(), tn.getParentId());
		}
	}
	/**
	 * 更新单个节点冗余信息
	 * @param treeCode
	 * @param nodeId
	 * @param newParentId
	 */
	private void updateNodeInfo(String treeCode, String nodeId, String parentId) {
		TreeNode node = getTreeNode(treeCode,nodeId);
		TreeNode parentNode = getTreeNode(treeCode,parentId);
		String idPath = (parentNode != null ? parentNode.getIdPath() : "") + "/" +node.getId();
		String namePath =(parentNode != null ? parentNode.getNamePath() : "") + "/" +node.getName();
		String codePath =(parentNode != null ? parentNode.getCodePath() : "") + "/" +node.getCode();
		int level = (parentNode!=null ? parentNode.getLevel() : 0) + 1; 
		String sql = " update t_sys_lov_tree_node set idPath = ?,namePath = ?,codePath = ?,parentId = ?,level = ? where treeCode = ? and id = ? ";
		daoTemplate.execute(sql,new Object[]{idPath,namePath,codePath,parentId,level,treeCode,nodeId});
	}
	
	@RequestMapping(value="/tree/addTreeNode",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int addTreeNode(
			@RequestParam(name="treeCode") String treeCode,
			@RequestParam(name="nodeId") String nodeId,
			@RequestParam(name="nodeName") String nodeName,
			@RequestParam(name="nodeCode") String nodeCode,
			@RequestParam(name="nodeSourceCode") String nodeSourceCode,
			@RequestParam(name="parentId") String parentId
		){
		Assert.hasLength(treeCode, "treeCode不能为空");
		Assert.hasLength(nodeId, "nodeId不能为空");
		Assert.hasLength(nodeName, "nodeName不能为空");
		Assert.hasLength(nodeCode, "nodeCode不能为空");
		Assert.hasLength(nodeSourceCode, "nodeSourceCode不能为空");
		Long count = daoTemplate.queryLong("select count(1) from t_sys_lov_tree_node where tree_code = ? and node_id = ? and node_source = ? " , new Object[]{treeCode,nodeId,nodeSourceCode});
		if(count > 0){
			throw new BugException("已经存在的节点，请检查数据一致性");
		}
		String sql = " insert into t_sys_lov_tree_node (treeCode,id,name,code,nodeSourceCode,parentId) values( ?, ?, ?, ?, ?, ? )";
		daoTemplate.execute(sql,new Object[]{treeCode,nodeId,nodeName,nodeCode,nodeSourceCode,parentId});
		int newVersion = move(treeCode, nodeId, parentId, -1);
		return newVersion;
	}
	
	/**
	 * 获得单个节点信息
	 */
	@RequestMapping(value="/tree/getTreeNode",method=RequestMethod.GET)
	public TreeNode getTreeNode(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId){
		return daoTemplate.queryObject(GET_TREE_NODE,new Object[]{treeCode,nodeId},TreeNode.class);
	}
	
	/**
	 * 获得整棵树的信息
	 */
	@RequestMapping(value="/tree/getAllTree",method=RequestMethod.GET)
	public Tree getAllTree(@RequestParam(name="treeCode") String treeCode){
		Tree tree = daoTemplate.queryObject(GET_TREE, new Object[]{treeCode}, Tree.class);
		List<TreeNode> treeNodeList = daoTemplate.query(GET_ALL_TREE_DATA_SQL, new Object[]{treeCode}, TreeNode.class);
		tree.setTreeNodeList(treeNodeList);
		return tree;
	}
	
	/**
	 * 获取树上某一节点的全部子节点
	 */
	@RequestMapping(value="/tree/getChildren",method=RequestMethod.GET)
	public List<TreeNode> getChildren(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId){
		return daoTemplate.query(GET_CHILDREN_SQL, new Object[]{treeCode}, TreeNode.class);
	}
	
	/**
	 * 获取树上某一节点的全部子孙节点
	 */
	@RequestMapping(value="/tree/getAllChildren",method=RequestMethod.GET)
	public List<TreeNode> getAllChildren(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId){
		return daoTemplate.query(GET_ALL_CHILDREN_SQL, new Object[]{"%"+treeCode+"%"}, TreeNode.class);
	}
	
	/**
	 * 更新树的名称
	 * @param treeCode
	 * @param name
	 */
	@RequestMapping(value="/tree/update",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void update(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="name") String name,@RequestParam(name="remark") String remark){
		daoTemplate.execute(UPDATE_TREE_SQL,new Object[]{name,remark,treeCode});
	}
	
	@RequestMapping(value="/tree/backup",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void backup(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="remark") String remark){
		String tableName = "tree_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String createTableSql = "create table ? as select * from v_sys_lov_tree_node where treeCode = ? " ;
		daoTemplate.execute(createTableSql,new Object[]{tableName,treeCode});
		String sql = "insert into t_sys_lov_tree_backup (id,treeCode,createDate,remark,tableName) values ( ? ,? ,? ,? ,? )";
		String rowId = StringUtils.getUUID();
		daoTemplate.execute(sql,new Object[]{rowId,treeCode,new Date(),remark,tableName});
	}
	
	/**
	 * 更新节点的信息
	 */
	@RequestMapping(value="/tree/updateTreeNode",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateTreeNode(
			@RequestParam(name="treeCode") String treeCode, 
			@RequestParam(name="nodeId") String nodeId, 
			@RequestParam(name="code") String code, 
			@RequestParam(name="name") String name
		){
		daoTemplate.execute(UPDATE_TREE_NODE_SQL,new Object[]{name,code,treeCode,nodeId});
		TreeNode treeNode = getTreeNode(treeCode, nodeId);
		updateAllNodeInfo(treeCode, nodeId, treeNode.getParentId());
	}
	
}
