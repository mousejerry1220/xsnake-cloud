package org.xsnake.cloud.web.bee.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xsnake.cloud.common.action.BaseAction;

@Controller
public class LoginAction extends BaseAction{

	@RequestMapping(value = "/login",method=RequestMethod.GET)
    public String login(){
        return forward("login");
    }
    
	@ResponseBody
	@RequestMapping(value = "/login",method=RequestMethod.POST)
    public String login(LoginForm loginForm){
		loginForm.validate();
		return sendSuccessMessage();
    }

}
