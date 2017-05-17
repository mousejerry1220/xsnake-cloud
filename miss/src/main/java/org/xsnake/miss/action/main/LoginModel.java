package org.xsnake.miss.action.main;

import java.io.Serializable;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 下午4:50:00 
* 
*/
public class LoginModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String password;
	
	private String username;

	private String randomCode;
	
	private String rememberMeFlag;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public String getRememberMeFlag() {
		return rememberMeFlag;
	}

	public void setRememberMeFlag(String rememberMeFlag) {
		this.rememberMeFlag = rememberMeFlag;
	}

}
