package com.chinaunicom.filterman.rsa.decrypt;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;

import com.chinaunicom.filterman.rsa.bean.RequestBean;
import com.chinaunicom.filterman.rsa.RSAEncryptUtil;
import com.chinaunicom.filterman.utilities.Logging;

/**
 * Copyright 2013 by eclipse 
 * All right reserved. 
 * Created on Sep 3, 2013 4:06:44
 * PM by wenhuan
 */

public class RequestDecrypt {
	private String publicKeyPath;
	
	private String privateKeyPath;

	public String decryptRequest(RequestBean request) {

		RSAEncryptUtil rsa = RSAEncryptUtil.getInstance();
		
		Key privateKey = null;
		try {
			privateKey = readPublicAndPrivateKey(true);
		} catch (Exception e) {
            Logging.logError("Doing RequestDecrypt.readPublicAndPrivateKey error occurred : ", e);
            return null;
		}

		String decryptContent = request.getMessage();

		StringBuffer decryptBuf = new StringBuffer();
		String[] dectyptArr = decryptContent.split("- -");
		for (int j = 0; j < dectyptArr.length; j++) {
			String decrypt = "";
			try {
				decrypt = rsa.decrypt(dectyptArr[j], privateKey);
			} catch (Exception e) {
                Logging.logError("Doing RequestDecrypt.decryptRequest error occurred : ", e);
                return null;
			}

			decryptBuf.append(decrypt);
		}

		return decryptBuf.toString();
	}

	public Key readPublicAndPrivateKey(boolean isPrivate) throws Exception {
		ObjectInputStream ois = null;
		try {
			if (isPrivate) {
				ois = new ObjectInputStream(new FileInputStream(privateKeyPath));
			} else {
				ois = new ObjectInputStream(new FileInputStream(publicKeyPath));
			}

			return (Key) ois.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			ois.close();
		}
	}

	public String getPublicKeyPath() {
		return publicKeyPath;
	}

	public void setPublicKeyPath(String publicKeyPath) {
		this.publicKeyPath = publicKeyPath;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}
}
