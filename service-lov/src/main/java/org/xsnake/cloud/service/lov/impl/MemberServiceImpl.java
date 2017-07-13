package org.xsnake.cloud.service.lov.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xsnake.cloud.common.business.IBaseService;
import org.xsnake.cloud.common.dao.DaoTemplate;
import org.xsnake.cloud.common.exception.BusinessException;
import org.xsnake.cloud.common.search.IPage;
import org.xsnake.cloud.common.utils.StringUtils;
import org.xsnake.cloud.service.lov.api.IGroupService;
import org.xsnake.cloud.service.lov.api.IMemberService;
import org.xsnake.cloud.service.lov.api.ITreeService;
import org.xsnake.cloud.service.lov.api.entity.LovGroup;
import org.xsnake.cloud.service.lov.api.entity.LovMember;
import org.xsnake.cloud.service.lov.api.entity.LovMemberCondition;
import org.xsnake.cloud.service.lov.api.entity.LovMemberForm;

@Service
@RestController
public class MemberServiceImpl implements IMemberService {

	private final String SAVE_SQL="insert into t_sys_lov_member(id,groupCode,code,name,remark,sn,searchKey) values ( ?, ?, ?, ?, ?, ?, ? )";
	
	/**
	 * 获取该类型存在哪些树中
	 */
	private final String GET_TREES_BY_SOURCE = "select * from t_sys_lov_tree_node_source where node_source_code = ? ";
	
	@Autowired
	DaoTemplate daoTemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	IGroupService groupService;
	
	@Autowired
	ITreeService treeService;
	
	@Autowired
	IBaseService baseService;
	
	public static final String BUSINESS_TYPE = "LOV_MEMBER";

	@RequestMapping(value="/member/queryForAll",method={RequestMethod.GET,RequestMethod.POST})
	public List<LovMember> queryForAll(@RequestParam("groupCode") String groupCode){
		StringBuffer sql = new StringBuffer("select * from v_sys_lov_member where groupCode = ? order by sn ");
		return daoTemplate.query(sql.toString(), new Object[]{groupCode},LovMember.class);
	}
	
	@RequestMapping(value="/member/queryForActive",method={RequestMethod.GET,RequestMethod.POST})
	public List<LovMember> queryForActive(@RequestParam("groupCode") String groupCode){
		StringBuffer sql = new StringBuffer("select * from v_sys_lov_member where groupCode = ? and deleteFlag !='N' order by sn ");
		return daoTemplate.query(sql.toString(), new Object[]{groupCode},LovMember.class);
	}
	
	@RequestMapping(value="/member/search",method=RequestMethod.GET)
	public IPage search(LovMemberCondition condition){
		Assert.notNull(condition);
		Assert.notNull(condition.getGroupCode());
		StringBuffer sql = new StringBuffer(" select * from v_sys_lov_member where groupCode = ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(condition.getGroupCode());
		if(!StringUtils.isEmpty(condition.getSearchKey())){
			sql.append(" and ( m.name like ? or m.code like ? or m.searchKey like ?) ");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
		}
		if(condition.isActive()){
			sql.append(" and deleteFlag != 'N' ");
		}
		if(condition.isDelete()){
			sql.append(" and deleteFlag == 'N' ");
		}
		sql.append(" order by sn ");
		return daoTemplate.search(sql.toString(), args.toArray(), condition.getPage(), condition.getPageSize());
	}
	
	@RequestMapping(value="/member/searchByTree",method=RequestMethod.GET)
	public IPage searchByTree(LovMemberCondition condition){
		Assert.notNull(condition);
		Assert.notNull(condition.getGroupCode());
		StringBuffer sql = new StringBuffer();
		sql.append("select m.*,n.parentId,n.namePath from v_sys_lov_member m, t_sys_lov_tree_node n where m.id = n.id and m.groupCode = n.nodeSourceCode and n.treeCode = m.groupCode and m.groupCode = ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(condition.getGroupCode());
		if(StringUtils.isEmpty(condition.getParentId())){
			sql.append(" and n.parentId is null ");
		}else{
			sql.append(" and n.parentId = ? ");
			args.add(condition.getParentId());
		}
		if(!StringUtils.isEmpty(condition.getSearchKey())){
			sql.append(" and ( m.name like ? or m.code like ? or m.search_key like ?) ");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
		}
		sql.append(" order by n.level,m.sn ");
		return daoTemplate.search(sql.toString(), args.toArray(), condition.getPage(), condition.getPageSize());
	}
	
	@RequestMapping(value="/member/save",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void save(LovMemberForm form) {
		Assert.notNull(form);
		form.validate();
		//验证是否已经存在
		String sql = "select count(1) from t_sys_lov_member where groupCode = ? and code = ? ";
		Long count = daoTemplate.queryLong(sql,new Object[]{form.getGroupCode(),form.getCode()});
		if(count > 0){
			throw new BusinessException("已经存在的编码"+ form.getCode());
		}
		
		LovGroup group = groupService.get(form.getGroupCode());
		if(group == null){
			throw new BusinessException("指定的groupCode不存在：" + form.getGroupCode());
		}
		//存储
		String id = StringUtils.getUUID();
		daoTemplate.execute(SAVE_SQL,new Object[]{
			id,
			form.getGroupCode(),
			form.getCode(),
			form.getName(),
			form.getRemark(),
			1,
			form.getSearchKey()
		});
		//检查存在哪些树中，并检查是否设置了父节点，然后把member插入到该树中
		List<String> list = getTreeByGroupCode(form.getGroupCode());
		for(String treeCode : list){
			String parentId = null;
			if(form.getTreeParentMap() != null){
				parentId = form.getTreeParentMap().get(treeCode);
			}
			treeService.addTreeNode(treeCode, id, form.getName(), form.getCode(), form.getGroupCode(), parentId);
		}
		baseService.recordSave(form.getId(), BUSINESS_TYPE, form);
	}

	private List<String> getTreeByGroupCode(String groupCode) {
		List<String> list = jdbcTemplate.query(GET_TREES_BY_SOURCE, new ArgumentPreparedStatementSetter(new Object[]{groupCode}), new SingleColumnRowMapper<String>(String.class));
		return list;
	}

	@RequestMapping(value="/member/get",method=RequestMethod.GET)
	public LovMember get(String id){
		String sql = "select * from v_sys_lov_member where id = ? ";
		return daoTemplate.queryObject(sql,new Object[]{id},LovMember.class);
	}
	
	@RequestMapping(value="/member/delete",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void delete(LovMemberForm form) {
		Assert.notNull(form);
		Assert.notNull(form.getId());
		baseService.recordDelete(form.getId(), BUSINESS_TYPE, form);
	}

	@RequestMapping(value="/member/update",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void update(LovMemberForm form){
		Assert.notNull(form);
		Assert.notNull(form.getId());
		String sql = "update t_sys_lov_member set code = ? ,name = ? ,remark = ? ,serachKey = ? where id = ? ";
		daoTemplate.execute(sql,new Object[]{form.getCode(),form.getName(),form.getRemark(),form.getSearchKey(),form.getId()});
		LovMember lovMember = get(form.getId());
		List<String> list = getTreeByGroupCode(lovMember.getGroupCode());
		for(String treeCode : list){
			treeService.updateTreeNode(treeCode, form.getId(), form.getCode(), form.getName());
		}
		baseService.recordUpdate(form.getId(), BUSINESS_TYPE, form);
	}
}
