package org.xsnake.miss.service.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.xsnake.dao.BaseDao;
import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.lov.IEntityDefinitionService;
import org.xsnake.miss.api.lov.entity.EntityAttributesDefinition;
import org.xsnake.miss.api.lov.entity.EntityDefinition;
import org.xsnake.miss.api.lov.entity.TreeDefinition;
import org.xsnake.miss.api.lov.entity.TreeMemberDefinition;
import org.xsnake.miss.api.lov.model.EntityDefinitionModel;
import org.xsnake.miss.exception.MissException;
import org.xsnake.web.common.BeanUtils;

/**
 * @author Jerry.Zhao
 * @version 创建时间：2017年5月17日 下午1:55:25
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EntityDefinitionServiceImpl implements IEntityDefinitionService {

	public final static String TABLE_PREFIX = "BIZ_T";
	
	@Autowired
	BaseDao baseDao;

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
				baseDao.save(attr);
			}
			// 比对更新具体的表
			updateTable(oldEntityDefinition.getId(), newAttributes, oldAttributes);
		}
	}

	/**
	 * 更新字段后，同步更新具体的表结构
	 * 
	 * @param id
	 * @param attributes
	 * @param oldAttributes
	 */
	private void updateTable(String id, List<EntityAttributesDefinition> newAttributes, List<EntityAttributesDefinition> oldAttributes) {
		Map<String, EntityAttributesDefinition> newMap = toMap(newAttributes);
		Map<String, EntityAttributesDefinition> oldMap = toMap(oldAttributes);
		Set<String> allKeys = allKeys(newAttributes, oldAttributes);
		String table =  TABLE_PREFIX + id;
		for (String key : allKeys) {
			EntityAttributesDefinition newEntityAttributesDefinition = newMap.get(key);
			EntityAttributesDefinition oldEntityAttributesDefinition = oldMap.get(key);
			String columnDDL = null;
			String remarkDDL = null;
			if (newEntityAttributesDefinition == null && oldEntityAttributesDefinition != null) {// 删除列
				columnDDL = "alter table "+table+" drop column " + key ;
			} else if (oldEntityAttributesDefinition == null && newEntityAttributesDefinition != null) {// 新增列
				columnDDL = "alter table "+table+" add "+key+" "+getColumnType(newEntityAttributesDefinition)+" " + ("N".equals(newEntityAttributesDefinition.getNullFlag()) ? "not null" : "");
				remarkDDL = "comment on column "+table+"."+key+" is '"+ newEntityAttributesDefinition.getRemark() +"'";
			} else {// 修改列
				columnDDL = "alter table "+table+" modify last_updated_date "+getColumnType(newEntityAttributesDefinition)+"  " + ("N".equals(newEntityAttributesDefinition.getNullFlag()) ? "not null" : "");
				remarkDDL = "comment on column "+table+"."+key+" is '"+ newEntityAttributesDefinition.getRemark() +"'";
			}
			if(columnDDL!=null){
				baseDao.executeSQL(columnDDL);
			}
			if(remarkDDL !=null){
				baseDao.executeSQL(remarkDDL);
			}
		}
	}

	private String getColumnType(EntityAttributesDefinition entityAttributesDefinition){
		String type = entityAttributesDefinition.getType();
		if("varchar2".equalsIgnoreCase(type)){
			type = type + "("+entityAttributesDefinition.getLength()+")";
		}else if("date".equalsIgnoreCase(type)){
			type = "date" ;
		}else if("number".equalsIgnoreCase(type)){
			type = type + "("+entityAttributesDefinition.getLength()+","+entityAttributesDefinition.getPrecision()+")";
		}else if ("fk".equalsIgnoreCase(type)){
			type = "varchar2(32)";
		}else{
			throw new MissException("1002");
		}
		return type;
	}
	
	private Map<String, EntityAttributesDefinition> toMap(List<EntityAttributesDefinition> attributes) {
		Map<String, EntityAttributesDefinition> map = new HashMap<String, EntityAttributesDefinition>();
		for (EntityAttributesDefinition d : attributes) {
			map.put(d.getFieldCode(), d);
		}
		return map;
	}

	private Set<String> allKeys(List<EntityAttributesDefinition> newAttributes,List<EntityAttributesDefinition> oldAttributes) {
		Set<String> keys = new HashSet<String>();
		for (EntityAttributesDefinition d : newAttributes) {
			keys.add(d.getFieldCode());
		}

		for (EntityAttributesDefinition d : oldAttributes) {
			keys.add(d.getFieldCode());
		}
		return keys;
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
		
		//创建表
		String table =  TABLE_PREFIX + entityDefinition.getId();
		StringBuffer ddl = new StringBuffer();
		ddl.append("CREATE TABLE ").append(table).append(" (");
		List<EntityAttributesDefinition> attributes = entityDefinitionModel.getAttributes();
		for(EntityAttributesDefinition attr : attributes){
			attr.validate();
			String nullFlag = "NOT NULL";
			if("Y".equals(attr.getNullFlag())){
				nullFlag = "";
			}
			ddl.append(String.format(" %s  %s  %s , ",attr.getFieldCode().toUpperCase(),getColumnType(attr),nullFlag));
		}
		ddl.append(" ROW_ID varchar2(32) not null ");
		ddl.append(" ) ");
		ddl.append(" pctfree 10 ");
		ddl.append(" initrans 1 ");
		ddl.append(" maxtrans 255 ");
		ddl.append(" storage ( ");
		ddl.append(" initial 64K ");
		ddl.append(" next 8K ");
		ddl.append(" minextents 1 ");
		ddl.append(" maxextents unlimited ");
		ddl.append(" ) ");
		baseDao.executeSQL(ddl.toString());
		
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
		baseDao.executeSQL("DROP TABLE "+TABLE_PREFIX+id );
		//TODO 查找many to many 与FK 关系的表
	}

	public IPage query(PageCondition condition) {
		QueryObject queryObject = getQueryObject(condition);
		return baseDao.search(queryObject.hql.toString(), queryObject.args.toArray(),condition.getRows(),condition.getPage());
	}

	private QueryObject getQueryObject(PageCondition condition) {
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

	public List<EntityDefinition> list(PageCondition condition) {
		QueryObject queryObject = getQueryObject(condition);
		if(StringUtils.isEmpty(condition.get("autocomplete"))){
			return baseDao.findEntity(queryObject.hql.toString(), queryObject.args.toArray());
		}else{
			return baseDao.findEntity(queryObject.hql.toString(), queryObject.args.toArray(),0,condition.getRows());
		}
	}

}
