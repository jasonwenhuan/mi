package com.chinaunicom.filterman.comm.vo;

import java.util.List;

public class RequestDataVO {
    private String total;
    private String page;
    private String records;
    private List<RequestVO> rows;
    
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public List<RequestVO> getRows() {
		return rows;
	}
	public void setRows(List<RequestVO> rows) {
		this.rows = rows;
	}
}
