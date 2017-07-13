package org.xsnake.cloud.web.bee.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xsnake.cloud.common.action.BaseAction;

@Controller
public class MainAction extends BaseAction{

	@RequestMapping("/main")
    public String login(){
        return forward("main");
    }
    
}
