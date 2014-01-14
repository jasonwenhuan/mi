package com.hyron.exception;

public class FacadeException extends Exception{
	public FacadeException(String msg) {
        super(msg);
    }

    public FacadeException(String msg, Exception e) {
        super(msg, e);
    }
}
