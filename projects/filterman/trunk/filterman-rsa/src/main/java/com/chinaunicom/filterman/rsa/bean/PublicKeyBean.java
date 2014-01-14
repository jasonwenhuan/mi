package com.chinaunicom.filterman.rsa.bean;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:38:59 AM 
 *  by wenhuan
 */
public class PublicKeyBean {
	private static final long serialVersionUID = -3498058468287375113L;
    private byte[] keyBytes;
    
    public PublicKeyBean(){}

	public byte[] getKeyBytes() {
		return keyBytes;
	}

	public void setKeyBytes(byte[] keyBytes) {
		this.keyBytes = keyBytes;
	}


    
}
