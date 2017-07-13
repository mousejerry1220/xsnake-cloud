package org.xsnake.cloud.web.bee.login;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.xsnake.cloud.common.form.ValidatorForm;

public class LoginForm extends ValidatorForm{

	private static final long serialVersionUID = 1L;

	@Email(message="用户名必须为邮箱格式")
	@NotEmpty(message="用户名不能为空")
	private String username;
	
	@NotEmpty(message="用户密码不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
