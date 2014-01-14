package com.hyron.exception;

public class BusinessException extends Exception{
	public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Exception e) {
        super(msg, e);
    }
}
