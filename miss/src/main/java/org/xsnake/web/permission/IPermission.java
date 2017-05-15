package org.xsnake.web.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 上午10:15:40 
* 
*/
public abstract class IPermission implements TemplateMethodModelEx,Serializable{

	private static final long serialVersionUID = 1L;
	
	private Set<String> permissionCodeSet;
	
	private Set<String> permissionURISet;
	
	public IPermission(Collection<String> permissionCodeCollection,Collection<String> permissionURICollection){
		permissionCodeSet = new HashSet<String>(permissionCodeCollection);
		permissionURISet = new HashSet<String>(permissionURICollection);
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
		
		return permissionCodeSet.contains(permissionCode);
	}
	
	public boolean hasPermission(String uri){
		return permissionURISet.contains(uri);
	}
	
	
}
