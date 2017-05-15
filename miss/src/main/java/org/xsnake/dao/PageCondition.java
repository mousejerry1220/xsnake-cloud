package org.xsnake.dao;

public class PageCondition extends Condition {

	private static final long serialVersionUID = 1L;

	private int rows = 20;

	private int page = 1;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
