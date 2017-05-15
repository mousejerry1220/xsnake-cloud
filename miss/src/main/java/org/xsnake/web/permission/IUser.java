package org.xsnake.web.permission;

import java.io.Serializable;
import java.util.Collection;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 上午9:11:47 
* 
*/
public abstract class IUser extends IPermission implements Serializable{

	public IUser(Collection<String> permissionCodeCollection,Collection<String> permissionURICollection) {
		super(permissionCodeCollection,permissionURICollection);
	}

	private static final long serialVersionUID = 1L;

	public abstract String getId();
	
	public abstract String getName();
	
	public abstract String getPositionId();
	
	public abstract String getPositionName();
	
	public abstract String getOrgId();
	
	public abstract String getOrgName();
	
	public abstract String getEmail();

	public abstract String getMobile();
	
}
