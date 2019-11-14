package com.rgk.workprocess.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;


public class JGridPage<T> {
	//页码
	private int page = 0;
	//总页数
	private int total = 0;
	//总记录数
	private long records;
	//记录
	private List<T> rows = new ArrayList<>();
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public JGridPage() {
		super();
	}
	
	
	public JGridPage(Page<T> obj) {
		this.page = obj.getNumber() + 1;
		this.total = obj.getTotalPages();
		this.records = obj.getTotalElements();
		this.rows = obj.getContent();
	}
	
	
}
