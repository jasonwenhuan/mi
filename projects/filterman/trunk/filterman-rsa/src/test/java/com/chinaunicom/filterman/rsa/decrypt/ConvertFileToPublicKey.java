package com.chinaunicom.filterman.rsa.decrypt;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class ConvertFileToPublicKey {
    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException{
//    	BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:/Users/wenhuan.HYRON-JS/Desktop/publicKey.dat"));
//    	ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//    	byte[] temp = new byte[1024];
//    	int size = 0;
//    	while ((size = in.read(temp)) != -1) {
//    	    out.write(temp, 0, size);
//    	}
//    	in.close();
    	
    	
    	Key filePublicKey = null;
		try {
			filePublicKey = readPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] pk = filePublicKey.getEncoded();
		
       /* String pk = bytesToString(filePublicKey.getEncoded());
    	
        //byte[] pk1 = stringToBytes(pk.substring(1,pk.length()-1));
        byte[] pk1 = stringToBytes(pk);*/
		PublicKey publicKey = 
		    KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pk));
		System.out.println(publicKey.toString());
    }
    /*public static byte[] byteToByteArray(byte[] publicKey){
    	byte[] clone = new byte[publicKey.length];
    	for (int i = 0; i < publicKey.length; i++) {
    	    clone[i] = Byte.parseByte(publicKey[i]);
    	}
    	return clone;
    }*/
   /* private static byte[] stringToBytes(String encrytpStr) {
    	String[] strArr = encrytpStr.split(" ");
    	int len = strArr.length;
    	byte[] clone = new byte[len];
    	for (int i = 0; i < len; i++) {
    	    clone[i] = Byte.parseByte(strArr[i]);
    	}
    	return clone;
    }

	private static String bytesToString(byte[] encrytpByte) {
    	String result = "";
    	for (Byte bytes : encrytpByte) {
    	    result += bytes.toString() + " ";
    	}
    	return result;
    }*/
    
    public static Key readPublicKey() throws Exception{
      	 ObjectInputStream ois = null;
           try {
              	ois = new ObjectInputStream(new FileInputStream("/home/hyron/filterman/keyfiles/publicKey.dat"));
               return (Key) ois.readObject();
           } catch (Exception e) {
               throw e;
           }
           finally{
               ois.close();
           }
      }
}
