package org.xsnake.cloud.service.lov.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xsnake.cloud.common.business.IBaseService;
import org.xsnake.cloud.common.dao.DaoTemplate;
import org.xsnake.cloud.common.exception.BusinessException;
import org.xsnake.cloud.common.search.IPage;
import org.xsnake.cloud.service.lov.api.IGroupService;
import org.xsnake.cloud.service.lov.api.ITreeService;
import org.xsnake.cloud.service.lov.api.entity.LovGroup;
import org.xsnake.cloud.service.lov.api.entity.LovGroupCondition;
import org.xsnake.cloud.service.lov.api.entity.LovGroupForm;
import org.xsnake.cloud.service.lov.api.entity.TreeForm;

@Service
@RestController
public class GroupServiceImpl implements IGroupService{
	
	@Autowired
	DaoTemplate daoTemplate;
	
	@Autowired
	ITreeService treeService;
	
	@Autowired
	IBaseService baseService;
	
	private final String SAVE_GROUP_SQL = " insert into t_sys_lov_group(code,name,remark,systemFlag,treeFlag) values (?,?,?,?,?)";
	
	private final String CHECK_EXIST_GROUP_SQL = " select count(1) from t_sys_lov_group where code = ? ";
	
	private final String UPDATE_GROUP_SQL = " update t_sys_lov_group set name = ? , remark = ? where code = ? ";
	
	private final String GET_GROUP_SQL = " select * from v_sys_lov_group g where code = ? ";
	
	private final String SERARCH_GROUP_SQL = " select * from v_sys_lov_group ";
	
	public static final String BUSINESS_TYPE = "LOV_GROUP";
	
	@Override
	@RequestMapping(value="/group/save",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void save(@RequestBody LovGroupForm lovGroupForm) {
		Assert.notNull(lovGroupForm, "LovGroupForm object must not be null");
		lovGroupForm.validate();
		Long count = daoTemplate.queryLong(CHECK_EXIST_GROUP_SQL,lovGroupForm.getCode());
		if(count > 0) {
			throw new BusinessException("已经存在的编码：" + lovGroupForm.getCode());
		}
		daoTemplate.execute(SAVE_GROUP_SQL,new Object[]{
			lovGroupForm.getCode(),
			lovGroupForm.getName(),
			lovGroupForm.getRemark(),
			"N",
			lovGroupForm.getTreeFlag() == null ? "N" : lovGroupForm.getTreeFlag()
		});
		baseService.recordSave(lovGroupForm.getCode(), BUSINESS_TYPE, lovGroupForm);
		
		if("Y".equals(lovGroupForm.getTreeFlag())){
			TreeForm treeForm = new TreeForm();
			BeanUtils.copyProperties(lovGroupForm, treeForm);
			Set<String> nodeSources = new HashSet<String>();
			nodeSources.add(lovGroupForm.getCode());
			treeForm.setNodeSources(nodeSources);
			treeService.save(treeForm);
		}
	}

	@Override
	@RequestMapping(value="/group/update",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void update(@RequestBody LovGroupForm lovGroupForm) {
		Assert.notNull(lovGroupForm);
		daoTemplate.execute(UPDATE_GROUP_SQL,new Object[]{
			lovGroupForm.getName(),
			lovGroupForm.getRemark(),
			lovGroupForm.getCode()
		});
	}

	@Override
	@RequestMapping(value="/group/delete",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void delete(@RequestBody LovGroupForm form) {
		baseService.recordDelete(form.getCode(), BUSINESS_TYPE, form);
	}
	
	@Override
	@RequestMapping(value="/group/search",method={RequestMethod.POST,RequestMethod.GET})
	public IPage search(LovGroupCondition condition) {
		Assert.notNull(condition,"condition object must not be null");
		StringBuffer sql = new StringBuffer(SERARCH_GROUP_SQL);
		List<Object> args = new ArrayList<Object>();
		sql.append(" where 1=1 ");
		if(!StringUtils.isEmpty(condition.getSearchKey())){
			sql.append(" and ( code like ? or name like ? or remark like ? )");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
			args.add("%"+condition.getSearchKey()+"%");
		}
		if(!StringUtils.isEmpty(condition.getTreeFlag())){
			sql.append(" and treeFlag = ? ");
			args.add(condition.getTreeFlag());
		}
		if(!StringUtils.isEmpty(condition.getSystemFlag())){
			sql.append(" and systemFlag = ? ");
			args.add(condition.getSystemFlag());
		}
		
		if(condition.isActive()){
			sql.append(" and deleteFlag != 'N' ");
		}
		if(condition.isDelete()){
			sql.append(" and deleteFlag == 'N' ");
		}
		return daoTemplate.search(sql.toString(), args.toArray(), condition.getPage(), condition.getPageSize());
	}

	@Override
	@RequestMapping(value="/group/get",method=RequestMethod.GET)
	public LovGroup get(@RequestParam(name="code") String code) {
		return daoTemplate.queryObject(GET_GROUP_SQL,code, LovGroup.class);
	}

}
