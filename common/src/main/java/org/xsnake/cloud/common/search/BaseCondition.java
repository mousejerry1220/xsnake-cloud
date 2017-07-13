package org.xsnake.cloud.common.search;

import java.io.Serializable;

public class BaseCondition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String searchKey;
	
	protected String orderBy;

	protected int page = 1;
	
	protected int pageSize = 20;
	
	protected String active = "Y"; //"Y"正常数据 "N"被删除数据  ""全部数据
	
	public String getOrderBy() {
		return orderBy;
	}

	public BaseCondition orderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public BaseCondition searchKey(String searchKey) {
		this.searchKey = searchKey;
		return this;
	}

	public int getPage() {
		return page;
	}

	public BaseCondition page(int page) {
		this.page = page;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public BaseCondition pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public boolean isActive(){
		return "Y".equals(active);
	}
	
	public boolean isDelete(){
		return "N".equals(active);
	}
	
	public boolean isAll(){
		return "".equals(active);
	}
	
	public BaseCondition active() {
		this.active = "Y";
		return this;
	}
	
	public BaseCondition delete() {
		this.active = "N";
		return this;
	}
	
	public BaseCondition all() {
		this.active = "";
		return this;
	}
	
}
