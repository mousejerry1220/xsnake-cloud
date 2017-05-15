package org.xsnake.miss.api.lov;

import org.xsnake.miss.api.lov.model.LovMemberModel;

public interface ILovMemberService {
	
	LovMemberModel get(String id);
	
	void update(LovMemberModel lovMemberModel);
	
	void save(LovMemberModel lovMemberModel);
	
	void delete(String id);
	
}
