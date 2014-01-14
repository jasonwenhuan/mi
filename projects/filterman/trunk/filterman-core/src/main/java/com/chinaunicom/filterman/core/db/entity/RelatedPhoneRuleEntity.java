package com.chinaunicom.filterman.core.db.entity;

public class RelatedPhoneRuleEntity {
	private String _id;
    private int interval;
    private int frequentLimit;
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getFrequentLimit() {
		return frequentLimit;
	}
	public void setFrequentLimit(int frequentLimit) {
		this.frequentLimit = frequentLimit;
	}
}
