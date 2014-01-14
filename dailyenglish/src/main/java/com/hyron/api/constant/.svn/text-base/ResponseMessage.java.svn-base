package com.hyron.api.constant;

public enum ResponseMessage {
	Success(0, "Success"), 
	LoginError(204,"Login error");
	
	
	private int code;
	private String errorMessage;
	private ResponseMessage(int code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}
	public int getCode() {
		return code;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public static ResponseMessage getMessageByCode(int code){
		for(ResponseMessage message : ResponseMessage.values()){
			if(message.code == code){
				return message;
			}
		}
		throw new IllegalArgumentException("response code is not support: " + code);
	}
}
