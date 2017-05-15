package org.xsnake.miss.api.lov;

import java.util.List;

import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.lov.entity.LovMember;
import org.xsnake.miss.api.lov.model.LovGroupModel;

public interface ILovGroupService {
	
	LovGroupModel get(String id);
	
	void update(LovGroupModel lovGroupModel);
	
	void save(LovGroupModel lovGroupModel);
	
	void delete(String id);
	
	IPage queryGroup(PageCondition condition);
	
	IPage queryMember(String id,PageCondition condition);
	
	List<LovMember> detail(String id);
	
}
