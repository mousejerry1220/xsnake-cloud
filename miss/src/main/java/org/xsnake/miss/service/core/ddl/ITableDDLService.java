package org.xsnake.miss.service.core.ddl;

import java.util.List;

import org.xsnake.miss.api.core.entity.EntityAttributesDefinition;
import org.xsnake.miss.api.core.model.EntityDefinitionModel;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月17日 下午10:13:10 
* 
*/
public interface ITableDDLService  {

	void createTable(String table,EntityDefinitionModel entityDefinitionModel);

	void updateTable(String string, List<EntityAttributesDefinition> newAttributes, List<EntityAttributesDefinition> oldAttributes);
	
}
