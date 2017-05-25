package org.xsnake.miss.action.main;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.xsnake.miss.api.permission.IPermissionService;
import org.xsnake.miss.api.permission.User;
import org.xsnake.web.BaseAction;

@Controller
public class MainAction extends BaseAction{
	
	@Autowired
	IPermissionService permissionService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		User user = getLoginUser();
		if(user != null){
			return redirect("/main");
		}
		return forward("login");
	}
	
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String main(){
		return forward("main");
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(LoginModel loginModel,HttpServletRequest request){
		User user = permissionService.login(loginModel.getUsername(), loginModel.getPassword());
		setSessionAttribute(LOGIN_USER, user);
		return sendSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="/changePosition",method=RequestMethod.POST)
	public String changePosition(String positionId){
		User user = getLoginUser();
		permissionService.changePosition(user.getId(), positionId);
		setSessionAttribute(LOGIN_USER, user);
		return sendSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping("/language_{language}")
	public String change(@PathVariable String language,HttpServletRequest request){
	    if("en".equals(language)){
	        Locale locale = new Locale("en", "US"); 
	        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
	        return sendSuccessMessage();
	    }
		Locale locale = new Locale("zh", "CN"); 
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
		return sendSuccessMessage();
	}
	
}
