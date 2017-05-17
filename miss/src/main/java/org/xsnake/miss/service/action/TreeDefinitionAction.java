package org.xsnake.miss.service.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xsnake.miss.api.core.ITreeService;
import org.xsnake.miss.api.core.model.TreeDefinitionModel;
import org.xsnake.web.BaseAction;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 上午10:47:12 
* 
*/
@Controller
@RequestMapping("/definition")
public class TreeDefinitionAction extends BaseAction{

	@Autowired
	ITreeService treeService;
	
	@RequestMapping(value="/treeDefinition",method=RequestMethod.GET)
	public String treeDefinition(){
		return forward("treeDefinition");
	}
	
	@RequestMapping(value="/treeDefinition",method=RequestMethod.POST)
	public String treeDefinition(TreeDefinitionModel treeModel){
		return forward("treeDefinition");
	}
}
