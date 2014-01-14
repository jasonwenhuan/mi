package com.chinaunicom.filterman.rsa.decrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.rsa.RSAEncryptUtil;
import com.chinaunicom.filterman.rsa.BaseTest;
import com.chinaunicom.filterman.rsa.decrypt.RequestDecrypt;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:42:43 AM 
 *  by wenhuan
 */
public class TestDecrypt extends BaseTest{
	public static final int MAXENCRYPT = 8;
	public static Key publicKey;
	public static Key privateKey;
	
/*	public static final String PUBLICKEYFILEPATH = "E:/china-unicom/keyfiles/publicKey.dat";
	public static final String PRIVATEKEYFILEPATH = "E:/china-unicom/keyfiles/privateKey.dat";*/
	
	@Autowired
    private RequestDecrypt requestDecrypt = (RequestDecrypt)ctx.getBean("requestDecrypt");
	
	@Test
    public void testEncryptAndDecrypt() throws Exception{
    	//String request = "{\"phone\": \"P00000000005\",\"mac\": \"D00000000005\",\"billid\": \"12345\",\"appid\": \"5\",\"ip\": \"172.29.13.40\",\"account\": \"A00000000005\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
    	//String request = "{\"phone\": \"P00000000002\",\"mac\": \"D00000000002\",\"billid\": \"12345\",\"appid\": \"2\",\"ip\": \"172.29.13.40\",\"account\": \"A00000000002\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
    	//for 2-2-2 validate
    	//String request = "{\"phone\": \"P00000000999\",\"mac\": \"D00000999\",\"billid\": \"12345\",\"appid\": \"999\",\"ip\": \"172.29.13.40\",\"account\": \"A00000000999\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
    	//String request = "{\"phone\": \"P00000000997\",\"mac\": \"D00000998\",\"billid\": \"12345\",\"appid\": \"998\",\"ip\": \"172.29.13.40\",\"account\": \"A00000000998\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
    	//for 1-1-1 validate
    	//String request = "{\"phone\": \"P00000000996\",\"mac\": \"D00000996\",\"billid\": \"12345\",\"appid\": \"996\",\"ip\": \"172.29.13.40\",\"account\": \"A00000000996\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
    	//String request = "{\"phone\": \"P00000001020\",\"mac\": \"D00001020\",\"billid\": \"12345\",\"appid\": \"1020\",\"ip\": \"172.29.13.40\",\"account\": \"A00000001020\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}";
		String request = "{\"phone\": \"P00000001020\",\"mac\": \"D00001020\",\"appid\": \"1020\",\"account\": \"A00000001020\"}";

    	System.out.println(new Date().getTime());
    	RSAEncryptUtil rsa = new RSAEncryptUtil();
    	rsa.init();

    	
    	Key privateKey = readPublicAndPrivateKey(true);
    	Key publicKey = readPublicAndPrivateKey(false);
    	
    	
    	StringBuffer sb = new StringBuffer();
    	int relen = request.length();
    	int subTimes = relen / MAXENCRYPT + 1;
    	for(int i=0;i<subTimes;i++){
    		int maxLen = i * MAXENCRYPT + MAXENCRYPT;
    		if(i == subTimes-1){
    			maxLen = relen;
    		}
    		String subRequest = request.substring(i * MAXENCRYPT, maxLen);
    		String encryptContent = rsa.encrypt(subRequest, publicKey);
    		sb.append(encryptContent);
    		sb.append("- -");
    	}
    	System.out.println(sb.toString());
    	//String encryptContent = rsa.encrypt(request, publicKey);
    	
    	String decryptContent = sb.toString();
    	
    	StringBuffer decryptBuf = new StringBuffer();
    	String[] dectyptArr = decryptContent.split("- -");
    	for(int j=0;j<dectyptArr.length;j++){
    		String decrypt = rsa.decrypt(dectyptArr[j], privateKey);
    		decryptBuf.append(decrypt);
    	}
    	
    	System.out.println(decryptBuf.toString());
    
    }
    
    public void writePublicAndPrivateKey() throws Exception{
    	ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        try {
            
            oos1 = new ObjectOutputStream(new FileOutputStream(requestDecrypt.getPublicKeyPath()));
            oos2 = new ObjectOutputStream(new FileOutputStream(requestDecrypt.getPrivateKeyPath()));
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
    
    public Key readPublicAndPrivateKey(boolean isPrivate) throws Exception{
    	 ObjectInputStream ois = null;
         try {
             if(isPrivate){
            	 ois = new ObjectInputStream(new FileInputStream(requestDecrypt.getPrivateKeyPath()));
             }else{
            	 ois = new ObjectInputStream(new FileInputStream(requestDecrypt.getPublicKeyPath()));
             }
             
             return (Key) ois.readObject();
         } catch (Exception e) {
             throw e;
         }
         finally{
             ois.close();
         }
    }
    
    public static void main(String[] args){
    	Date d = new Date();
    	System.out.println(d.getTime());
    }
}
