package com.chinaunicom.filterman.rsa.bean;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:38:54 AM 
 *  by wenhuan
 */
public class Path {
    private String rootPath;
    private String privateKeyPath;
    private String publicKeyPath;
    private String messagePath;
    
    public Path(){}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}

	public String getPublicKeyPath() {
		return publicKeyPath;
	}

	public void setPublicKeyPath(String publicKeyPath) {
		this.publicKeyPath = publicKeyPath;
	}

	public String getMessagePath() {
		return messagePath;
	}

	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}	
}
