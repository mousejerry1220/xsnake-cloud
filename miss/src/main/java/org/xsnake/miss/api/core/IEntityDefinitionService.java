package org.xsnake.miss.api.core;

import java.util.List;

import org.xsnake.dao.Condition;
import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.core.entity.EntityDefinition;
import org.xsnake.miss.api.core.model.EntityDefinitionModel;

public interface IEntityDefinitionService {
	
	EntityDefinitionModel get(String id);
	
	void update(EntityDefinitionModel entityDefinitionModel);
	
	void save(EntityDefinitionModel entityDefinitionModel);
	
	void delete(String id);
	
	IPage query(PageCondition condition);
	
	List<EntityDefinition> list(Condition condition);
}
