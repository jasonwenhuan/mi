package com.hyron.api.bean;

import com.hyron.api.constant.ResponseMessage;

public class State {
	private Integer code;
	private String message;
	public State(ResponseMessage rm){
		code = rm.getCode();
		message = rm.getErrorMessage();
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
