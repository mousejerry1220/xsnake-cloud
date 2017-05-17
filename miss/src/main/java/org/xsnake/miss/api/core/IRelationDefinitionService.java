package org.xsnake.miss.api.core;

import java.util.List;

import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.core.entity.RelationDefinition;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 上午11:18:50 
* 
*/
public interface IRelationDefinitionService {
	
	RelationDefinition get(String id);
	
	void update(RelationDefinition relationDefinition);
	
	void save(RelationDefinition relationDefinition);
	
	void delete(String id);
	
	IPage query(PageCondition condition);
	
	List<RelationDefinition> relationList(String entityDefinitionId);
	
}
