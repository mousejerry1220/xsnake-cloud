package org.xsnake.miss.service.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.xsnake.dao.BaseDao;
import org.xsnake.dao.Condition;
import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.core.IEntityDefinitionService;
import org.xsnake.miss.api.core.entity.EntityAttributesDefinition;
import org.xsnake.miss.api.core.entity.EntityDefinition;
import org.xsnake.miss.api.core.entity.TreeDefinition;
import org.xsnake.miss.api.core.entity.TreeMemberDefinition;
import org.xsnake.miss.api.core.model.EntityDefinitionModel;
import org.xsnake.miss.exception.MissException;
import org.xsnake.miss.service.core.ddl.ITableDDLService;
import org.xsnake.web.common.BeanUtils;

/**
 * @author Jerry.Zhao
 * @version 创建时间：2017年5月17日 下午1:55:25
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EntityDefinitionServiceImpl implements IEntityDefinitionService {
	
	@Autowired
	BaseDao baseDao;

	@Autowired
	ITableDDLService tableDDLService;
	
	@Transactional(readOnly = true)
	public EntityDefinitionModel get(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		EntityDefinition entityDefinition = baseDao.get(EntityDefinition.class, id);
		List<EntityAttributesDefinition> attributes = baseDao
				.findEntity(" from EntityAttributesDefinition where groupId = ? order by sortNo ");
		return new EntityDefinitionModel(entityDefinition, attributes);
	}

	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void update(EntityDefinitionModel entityDefinitionModel) {
		if (entityDefinitionModel == null) {
			throw new MissException("1000");
		}

		if (entityDefinitionModel.getEntityDefinition() == null || StringUtils.isEmpty(entityDefinitionModel.getEntityDefinition().getId())) {
			throw new MissException("1001");
		}

		EntityDefinition entityDefinition = entityDefinitionModel.getEntityDefinition();
		entityDefinition.validate();
		
		EntityDefinition oldEntityDefinition = baseDao.get(EntityDefinition.class, entityDefinition.getId());
		BeanUtils.copyPropertiesIgnoreNull(entityDefinition, oldEntityDefinition);
		baseDao.update(oldEntityDefinition);

		if (entityDefinitionModel.getAttributes() != null) {
			List<EntityAttributesDefinition> newAttributes = entityDefinitionModel.getAttributes();
			List<EntityAttributesDefinition> oldAttributes = baseDao.findEntity(" from EntityAttributesDefinition where entityDefinitionId = ? ", entityDefinition.getId());
			// 更新字段
			baseDao.executeHQL(" delete from EntityAttributesDefinition where entityDefinitionId = ? ", entityDefinition.getId());
			for (EntityAttributesDefinition attr : newAttributes) {
				attr.validate();
				attr.setEntityDefinitionId(entityDefinition.getId());
				baseDao.save(attr);
			}
			// 比对更新具体的表
			tableDDLService.updateTable(oldEntityDefinition.getId(), newAttributes, oldAttributes);
		}
	}
	
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void save(EntityDefinitionModel entityDefinitionModel) {
		if (entityDefinitionModel == null) {
			throw new MissException("1003");
		}

		if (entityDefinitionModel.getEntityDefinition() == null) {
			throw new MissException("1004");
		}
		
		EntityDefinition entityDefinition = entityDefinitionModel.getEntityDefinition();
		entityDefinition.validate();
		entityDefinition.setSystemFlag("N");
		baseDao.save(entityDefinition);
		
		//保存属性字段
		if (entityDefinitionModel.getAttributes() != null) {
			List<EntityAttributesDefinition> newAttributes = entityDefinitionModel.getAttributes();
			for (EntityAttributesDefinition attr : newAttributes) {
				attr.validate();
				attr.setEntityDefinitionId(entityDefinition.getId());
				baseDao.save(attr);
			}
		}
		
		//创建数据库表
		tableDDLService.createTable(entityDefinition.getId(),entityDefinitionModel);
		
		//如果是树，则生成树
		if("Y".equals(entityDefinition.getTreeFlag())){
			TreeDefinition treeDefinition = new TreeDefinition();
			treeDefinition.setName(entityDefinition.getName());
			treeDefinition.setSingleFlag("Y");
			baseDao.save(treeDefinition);
			
			TreeMemberDefinition treeMemberDefinition = new TreeMemberDefinition();
			treeMemberDefinition.setEntityDefinitionId(entityDefinition.getId());
			treeMemberDefinition.setTreeDefinitionId(treeDefinition.getId());
			treeMemberDefinition.setParentEntityDefinitionId(entityDefinition.getId());
			treeMemberDefinition.setLeafFlag("N");
			baseDao.save(treeMemberDefinition);
		}
	}

	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void delete(String id) {
		if(StringUtils.isEmpty(id)){
			throw new MissException("1005");
		}
		EntityDefinition entityDefinition = baseDao.get(EntityDefinition.class, id);
		//系统数据，不允许删除
		if("Y".equals(entityDefinition.getSystemFlag())){
			throw new MissException("1006");
		}
		baseDao.executeHQL(" delete from EntityAttributesDefinition where entityDefinitionId = ? ",id);
		baseDao.delete(entityDefinition);
		baseDao.executeSQL("DROP TABLE "+id );
		//TODO 查找many to many 与FK 关系的表
	}

	@Transactional(readOnly = true)
	public IPage query(PageCondition condition) {
		QueryObject queryObject = getQueryObject(condition);
		return baseDao.search(queryObject.hql.toString(), queryObject.args.toArray(),condition.getRows(),condition.getPage());
	}

	private QueryObject getQueryObject(Condition condition) {
		QueryObject queryObject = new QueryObject();
		StringBuffer hql = queryObject.hql;
		hql.append(" from EntityDefinition g where 1 = 1 ");
		List<Object> args = queryObject.args;
		if(!StringUtils.isEmpty(condition.get("searchKey"))){
			hql.append(" and ( g.id like ? or g.name like ? or g.remark like ? ) ");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
		}
		if(!StringUtils.isEmpty(condition.get("systemFlag"))){
			hql.append(" and g.systemFlag = ? ");
			args.add(condition.get("systemFlag"));
		}
		
		if(!StringUtils.isEmpty(condition.get("status"))){
			hql.append(" and g.status = ? ");
			args.add(condition.get("status"));
		}
		
		hql.append(" order by g.sortNo ");
		return queryObject;
	}
	
	private static class QueryObject{
		StringBuffer hql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
	}

	@Transactional(readOnly = true)
	public List<EntityDefinition> list(Condition condition) {
		QueryObject queryObject = getQueryObject(condition);
		if("Y".equals(condition.get("autocomplete"))){
			return baseDao.findEntity(queryObject.hql.toString(), queryObject.args.toArray());
		}else{
			return baseDao.findEntity(queryObject.hql.toString(), queryObject.args.toArray(),0,20);
		}
	}

}
