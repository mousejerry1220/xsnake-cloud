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
import org.xsnake.miss.api.core.IRelationDefinitionService;
import org.xsnake.miss.api.core.entity.RelationDefinition;
import org.xsnake.miss.exception.MissException;
import org.xsnake.web.common.BeanUtils;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 下午2:47:16 
* 
*/
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class RelationDefinitionServiceImpl implements IRelationDefinitionService{
	 
	@Autowired
	BaseDao baseDao;
	
	public RelationDefinition get(String id) {
		return baseDao.get(RelationDefinition.class, id);
	}

	public void update(RelationDefinition relationDefinition) {
		if(relationDefinition == null ){
			throw new MissException("1060");
		}
		if (StringUtils.isEmpty(relationDefinition.getId())) {
			throw new MissException("1061");
		}
		
		RelationDefinition oldRelationDefinition = baseDao.get(RelationDefinition.class, relationDefinition.getId());
		BeanUtils.copyPropertiesIgnoreNull(relationDefinition, oldRelationDefinition);
		baseDao.update(oldRelationDefinition);
	}

	public void save(RelationDefinition relationDefinition) {
		baseDao.update(relationDefinition);
	}

	public void delete(String id) {
		baseDao.deleteById(RelationDefinition.class, id);
	}

	public IPage query(PageCondition condition) {
		QueryObject queryObject = getQueryObject(condition);
		return baseDao.search(queryObject.hql.toString(), queryObject.args.toArray(),condition.getRows(),condition.getPage());
	}

	private static class QueryObject{
		StringBuffer hql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
	}
	
	private QueryObject getQueryObject(Condition condition) {
		QueryObject queryObject = new QueryObject();
		StringBuffer hql = queryObject.hql;
		hql.append(" from RelationDefinition g where 1 = 1 ");
		List<Object> args = queryObject.args;
		if(!StringUtils.isEmpty(condition.get("searchKey"))){
			hql.append(" and ( g.code like ? or g.name like ? or g.remark like ? ) ");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
		}
		return queryObject;
	}
	
	public List<RelationDefinition> relationList(String entityDefinitionId){
		if(StringUtils.isEmpty(entityDefinitionId)){
			return new ArrayList<RelationDefinition>();
		}
		String leftHql = " from RelationDefinition g where g.leftEntityDefinitionId = ? and g.leftMainFlag = 'Y' ";
		String rightHql = " from RelationDefinition g where g.rightEntityDefinitionId = ? and g.leftMainFlag = 'Y' ";
		List<RelationDefinition> leftList = baseDao.findEntity(leftHql.toString() , entityDefinitionId);
		List<RelationDefinition> rightList = baseDao.findEntity(rightHql.toString() , entityDefinitionId);
		leftList.addAll(rightList);
		return leftList;
	}

}
