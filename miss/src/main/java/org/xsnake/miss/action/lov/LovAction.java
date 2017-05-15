package org.xsnake.miss.action.lov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xsnake.miss.api.lov.ILovGroupService;
import org.xsnake.miss.api.lov.ILovMemberService;
import org.xsnake.web.BaseAction;

@Controller
@RequestMapping("/test")
public class LovAction extends BaseAction{
	
	@Autowired
	ILovGroupService lovGroupService;
	
	@Autowired
	ILovMemberService lovMemberService;
	
	@RequestMapping("/test")
	public String test(){
//		LovGroup lovGroup = new LovGroup();
//		List<LovGroupAttributes> attributes = new ArrayList<LovGroupAttributes>();
//		lovGroup.setId("TEST_A");
//		lovGroup.setName("TEST_A2");
//		
//		LovGroupAttributes attr = new LovGroupAttributes();
//		attr.setFieldCode("AA");
//		attr.setFieldName("AA_NAME");
//		attr.setLength(100);
//		attr.setNullFlag("Y");
//		
//		attributes.add(attr);
//		
//		attr = new LovGroupAttributes();
//		attr.setFieldCode("BB");
//		attr.setFieldName("BB_NAME");
//		attr.setLength(200);
//		attr.setNullFlag("N");
//		
//		attributes.add(attr);
//
//		
//		LovGroupModel lovGroupModel = new LovGroupModel(lovGroup,attributes);
//		lovGroupService.save(lovGroupModel);
		
		lovMemberService.get("1");
		
		return forward("test");
	}
}
