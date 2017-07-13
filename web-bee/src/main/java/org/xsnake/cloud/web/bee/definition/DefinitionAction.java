package org.xsnake.cloud.web.bee.definition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xsnake.cloud.common.action.BaseAction;

@Controller
public class DefinitionAction extends BaseAction{

	@RequestMapping(value = "/definitionGroup",method=RequestMethod.GET)
    public String definitionGroup(){
        return forward("definitionGroup");
    }
}
