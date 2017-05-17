package org.xsnake.miss.api.permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xsnake.miss.api.permission.entity.Permission;
import org.xsnake.web.common.BeanUtils;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 上午9:13:56 
* 
*/
public class Menu extends Permission implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String parentId; 
	
	public Menu(Permission permission){
		BeanUtils.copyProperties(permission, this);
		childrenList = new ArrayList<Menu>();
	}
	
	List<Menu> childrenList;
	
	public void addChild(Menu menu){
		childrenList.add(menu);
	}

	public List<Menu> getChildrenList() {
		return childrenList;
	}

	public String getParentId() {
		return parentId;
	}
	
}