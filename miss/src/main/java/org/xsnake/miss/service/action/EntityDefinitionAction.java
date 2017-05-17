package org.xsnake.miss.service.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xsnake.dao.Condition;
import org.xsnake.miss.api.core.IEntityDefinitionService;
import org.xsnake.miss.api.core.entity.EntityDefinition;
import org.xsnake.miss.api.core.model.EntityDefinitionModel;
import org.xsnake.web.BaseAction;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 上午9:46:18 
* 
*/
@Controller
@RequestMapping("/definition")
public class EntityDefinitionAction extends BaseAction{
	
	@Autowired
	IEntityDefinitionService entityDefinitionService;
	
	@RequestMapping(value="/entityDefinition",method=RequestMethod.GET)
	public String entityDefinition(){
		return forward("entityDefinition");
	}
	
	@ResponseBody
	@RequestMapping(value="/entityDefinition",method=RequestMethod.POST)
	public String entityDefinition(EntityDefinitionModel entityDefinitionModel){
		entityDefinitionService.save(entityDefinitionModel);
		return sendSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="entityAutoComplete",method=RequestMethod.GET)
	public String entityAutoComplete(Condition condition){
		condition.set("autocomplete","Y");
		List<EntityDefinition> list = entityDefinitionService.list(condition);
		return sendSuccessMessage(list);
	}
	
	
}
