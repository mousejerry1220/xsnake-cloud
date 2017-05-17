package org.xsnake.miss.api.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.xsnake.miss.api.permission.entity.Employee;
import org.xsnake.miss.api.permission.entity.Org;
import org.xsnake.miss.api.permission.entity.Permission;
import org.xsnake.miss.api.permission.entity.Position;
import org.xsnake.web.common.BeanUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 上午9:11:47 
* 
*/
public class User extends Employee implements TemplateMethodModelEx, Serializable{

	private static final long serialVersionUID = 1L;

	WebPermission webPermission;
	
	Org org;
	
	Position position;
	
	public User(Org org , Position position ,Employee employee,Collection<Permission> permissionCollection,Collection<Menu> menuCollection) {
		webPermission = new WebPermission(permissionCollection,menuCollection);
		BeanUtils.copyProperties(employee, this);
		this.org = org;
		this.position = position;
	}

	@SuppressWarnings("rawtypes")
	public Object exec(List args) throws TemplateModelException {
		if(args == null || args.size() != 1){
			throw new TemplateModelException("参数错误，有且只有一个参数：权限代码");
		}
		Object _permissionCode = args.get(0);
		if(_permissionCode == null){
			throw new TemplateModelException("参数错误，权限代码不能为空");
		}
		if(!(_permissionCode instanceof String)){
			throw new TemplateModelException("参数错误，权限代码必须为字符串");
		}
		String permissionCode = (String)_permissionCode;
		return webPermission.hasPermissionCode(permissionCode);
	}


	public Org getOrg() {
		return org;
	}

	public Position getPosition() {
		return position;
	}
	
}
