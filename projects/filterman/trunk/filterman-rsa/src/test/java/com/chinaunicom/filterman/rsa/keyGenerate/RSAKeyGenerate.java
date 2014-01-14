package com.chinaunicom.filterman.rsa.keyGenerate;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.rsa.bean.Path;
import com.chinaunicom.filterman.rsa.RSAEncryptUtil;
import com.chinaunicom.filterman.rsa.BaseTest;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:42:54 AM 
 *  by wenhuan
 */
public class RSAKeyGenerate extends BaseTest {

	@Autowired
	private Path path = (Path) ctx.getBean("path");

	@Test
	public void testGenerateKey() throws Exception {
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
		System.out.println(keys.getPublic().toString());
		System.out.println(keys.getPrivate().toString());

		writePublicAndPrivateKey(publicKey, privateKey);
	}
	
	public void writePublicAndPrivateKey(Key publicKey, Key privateKey) throws Exception{
    	ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        try {
            
            oos1 = new ObjectOutputStream(new FileOutputStream(path.getPublicKeyPath()));
            oos2 = new ObjectOutputStream(new FileOutputStream(path.getPrivateKeyPath()));
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
