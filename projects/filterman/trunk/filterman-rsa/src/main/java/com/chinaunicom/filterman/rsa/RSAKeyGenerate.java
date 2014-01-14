package com.chinaunicom.filterman.rsa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:42:54 AM 
 *  by wenhuan
 */
public class RSAKeyGenerate {

	public static void main(String[] args) throws Exception {
		RSAKeyGenerate generater = new RSAKeyGenerate();
		RSAEncryptUtil rsa = new RSAEncryptUtil();
		rsa.init();
		KeyPair keys = null;
		Key publicKey = null;
		Key privateKey = null;
		try {
			keys = rsa.generateKey();
			publicKey = keys.getPublic();
			privateKey = keys.getPrivate();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String[] keyfilePath = generater.getKeyFilePath();
		generater.writePublicAndPrivateKey(publicKey, privateKey, keyfilePath);
	}
	
	public String[] getKeyFilePath() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("filterman-rsa.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String[] keyFilePath = new String[2];
		keyFilePath[0] = p.getProperty("rsa.privatekeyfile");
		keyFilePath[1] = p.getProperty("rsa.publickeyfile");
		return keyFilePath;
	}
	
	public void writePublicAndPrivateKey(Key publicKey, Key privateKey, String[] keyfilePath) throws Exception{
    	ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        try {
            
            oos1 = new ObjectOutputStream(new FileOutputStream(keyfilePath[0]));
            oos2 = new ObjectOutputStream(new FileOutputStream(keyfilePath[1]));
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
        } catch (Exception e) {
            throw e;
        }
        finally{
            oos1.close();
            oos2.close();
        }
    }
}
