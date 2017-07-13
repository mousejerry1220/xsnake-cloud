package org.xsnake.cloud.service.lov.api;
/**
 * 这里将树的定义与树的节点操作全部融为一个服务内，所有的节点都是一个真实已存在的实体对象，树中的节点均为引用。
 * 每个树在定义时候可以有多个NODE_SOURCE。用意在于，可以将不同类型的多个实体组合为一个树。
 * 
 * 关于子节点：
 * 本来的设计会设计一个字段来标示是否为子节点，为了贴切现实世界，树的节点往往是通过其自身类型决定的。
 * 如：组织和岗位两者组成的树中，所有的岗位节点必定是叶子节点。
 * 但是如果是组织，岗位，员工组成的树中，则所有的员工节点必定为员工节点。
 * 
 * 关于节点：
 * 树有可能是在节点已经存在的时候后期创建的，这时候就会通过反向查找添加所有需要加入树中的节点，初始化时都是默认放在跟节点
 * 树的节点不存在被删除的风险，第一树的节点均为引用，可以通过“补漏”的方式重新载入缺少的节点，第二删除的设计完全为逻辑删除，
 * 但是在对树节点进行删除时候确定是要删除实际的数据节点。
 * 
 * 关于树的备份：
 * 我们可以在某一时刻把当前的树拷贝出一个快照来以供日后参考，拷贝出来的树是通过当前时间+树名称来命名的表，并记录在备份记录表中t_sys_lov_tree_backup
 * 
 * 冗余字段：
 * 节点中存在一些冗余字段记录与引用实体相同的名称，编码，还有在树中的路径（Path信息）
 * 非常重要：这些冗余字段都必须在实体节点对象被更新时调用更新方法已保持同步。
 */
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xsnake.cloud.common.feign.FeignConfiguration;
import org.xsnake.cloud.service.lov.api.entity.Tree;
import org.xsnake.cloud.service.lov.api.entity.TreeForm;
import org.xsnake.cloud.service.lov.api.entity.TreeNode;

@FeignClient(name = "lov", configuration = FeignConfiguration.class)
public interface ITreeService {

	@RequestMapping(value="/tree/save",method=RequestMethod.POST)
	void save(TreeForm form);
	
	@RequestMapping(value="/tree/save",method=RequestMethod.POST)
	void update(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="name") String name,@RequestParam(name="remark") String remark);
	
	@RequestMapping(value="/tree/getTreeNode",method={RequestMethod.GET,RequestMethod.POST})
	TreeNode getTreeNode(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId);
	
	@RequestMapping(value="/tree/getAllTree",method={RequestMethod.GET,RequestMethod.POST})
	Tree getAllTree(@RequestParam(name="treeCode") String treeCode);
	
	@RequestMapping(value="/tree/getChildren",method={RequestMethod.GET,RequestMethod.POST})
	List<TreeNode> getChildren(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId);
	
	@RequestMapping(value="/tree/getAllChildren",method={RequestMethod.GET,RequestMethod.POST})
	List<TreeNode> getAllChildren(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="nodeId") String nodeId);
	
	@RequestMapping(value="/tree/move",method=RequestMethod.POST)
	int move(
			@RequestParam(name="treeCode") String treeCode,
			@RequestParam(name="nodeId") String nodeId,
			@RequestParam(name="parentId") String parentId,
			@RequestParam(name="version") int version
		);
	
	@RequestMapping(value="/tree/backup",method=RequestMethod.POST)
	void backup(@RequestParam(name="treeCode") String treeCode,@RequestParam(name="remark") String remark);
	
	@RequestMapping(value="/tree/addTreeNode",method=RequestMethod.POST)
	int addTreeNode(
			@RequestParam(name="treeCode") String treeCode,
			@RequestParam(name="nodeId") String nodeId,
			@RequestParam(name="nodeName") String nodeName,
			@RequestParam(name="nodeCode") String nodeCode,
			@RequestParam(name="nodeSourceCode") String nodeSourceCode,
			@RequestParam(name="parentId") String parentId
		);
	
	@RequestMapping(value="/tree/updateTreeNode",method=RequestMethod.POST)
	void updateTreeNode(
			@RequestParam(name="treeCode") String treeCode, 
			@RequestParam(name="nodeId") String nodeId, 
			@RequestParam(name="code") String code, 
			@RequestParam(name="name") String name
		);
}
