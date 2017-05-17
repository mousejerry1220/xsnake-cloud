package org.xsnake.miss.api.permission.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月19日 上午1:08:39 
* 
*/
@Entity
@Table(name = "SYS_PERMISSION_PERMISSION")
public class Permission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SYS_PERMISSION_PERMISSION_ID_GENERATOR")
	@GenericGenerator(name="SYS_PERMISSION_PERMISSION_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	protected String id;
	
	@Column(name="NAME")
	protected String name;
	
	@Column(name="CODE")
	protected String code;
	
	@Column(name="PATH")
	protected String path;

	/**
	 * 0 -> 1 如果是菜单，上级必须是系统
	 * 1 -> 2 如果是虚拟集合 上级必须是菜单
	 * 1 -> 3
	 * 2 -> 3 如果是权限点既可以挂在菜单上，也可以挂在集合上
	 */
	@Column(name="TYPE")
	protected String type;//0系统，1菜单，2虚拟集合，3权限点
	
	@Column(name="PARENT_ID")
	protected String parentId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
