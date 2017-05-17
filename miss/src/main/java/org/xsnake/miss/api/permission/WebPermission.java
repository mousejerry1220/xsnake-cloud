package org.xsnake.miss.api.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.xsnake.miss.api.permission.entity.Permission;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 上午10:15:40 
* 
*/
public class WebPermission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Collection<String> permissionCodeSet;
	
	private Collection<String> permissionURISet;
	
	private Collection<Menu> menuList;
	
	public WebPermission(Collection<Permission> permissionCollection,Collection<Menu> menuCollection){
		permissionCodeSet = new HashSet<String>();
		permissionURISet = new HashSet<String>();
		for(Permission permission : permissionCollection){
			permissionCodeSet.add(permission.getCode());
			permissionURISet.add(permission.getPath());
		}
		menuList = menuCollection;
	}
	
	public boolean hasPermissionURI(String uri){
		return permissionURISet.contains(uri);
	}
	
	public boolean hasPermissionCode(String code){
		return permissionCodeSet.contains(code);
	}

	public Collection<Menu> getMenuList() {
		return Collections.unmodifiableCollection(menuList);
	}
	
}
