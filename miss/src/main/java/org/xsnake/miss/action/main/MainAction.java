package org.xsnake.miss.action.main;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.xsnake.web.BaseAction;

@Controller
public class MainAction extends BaseAction{
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return forward("login");
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(LoginModel loginModel){
		return forward("login");
	}
	
	@ResponseBody
	@RequestMapping("/language_{language}")
	public String change(@PathVariable String language,HttpServletRequest request){
		if(language == null){
			Locale locale = new Locale("zh", "CN"); 
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
			return sendSuccessMessage();
		}
		if(language.equals("zh")){
	        Locale locale = new Locale("zh", "CN"); 
	        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
	        return sendSuccessMessage();
	    }
	    else if(language.equals("en")){
	        Locale locale = new Locale("en", "US"); 
	        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
	        return sendSuccessMessage();
	    }
		Locale locale = new Locale("zh", "CN"); 
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
		return sendSuccessMessage();
	}
	
}
