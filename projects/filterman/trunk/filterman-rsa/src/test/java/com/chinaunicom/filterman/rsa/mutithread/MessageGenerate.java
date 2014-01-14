package com.chinaunicom.filterman.rsa.mutithread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.rsa.bean.Path;
import com.chinaunicom.filterman.rsa.BaseTest;

/**
 *  Copyright 2013 by eclipse 
 *  All right reserved.
 *  Created on Sep 16, 2013 10:43:03 AM 
 *  by wenhuan
 */
public class MessageGenerate extends BaseTest{
	 
	 @Autowired
	 private Path path = (Path)ctx.getBean("path");
	
     public static void main(String[] args){
    	 MessageGenerate messageGenerate = new MessageGenerate();
    	 messageGenerate.generateMessage();
    	
     }
     
     public void generateMessage(){
    	 StringBuffer sb = new StringBuffer();
    	 for(int i=0;i<2;i++){
    		 Random rand = new Random();
    		 int phone = rand.nextInt(1000000000);
    		 String phoneStr = "P00000000000".substring(0, "P00000000000".length() - String.valueOf(phone).length()) + phone;
    		 int device = rand.nextInt(1000000000);
    		 String deviceStr = "D00000000000".substring(0, "D00000000000".length() - String.valueOf(device).length()) + device;
    		 int account = rand.nextInt(1000000000);
    		 String accountStr = "A00000000000".substring(0, "A00000000000".length() - String.valueOf(account).length()) + account;
    		 int appid = rand.nextInt(100) + 1;
        	 sb.append("{\"phone\": \"");
        	 sb.append(phoneStr);
        	 sb.append("\",\"mac\": \"");
        	 sb.append(deviceStr);
        	 sb.append("\",\"billid\": \"12345\",\"appid\": \"");
        	 sb.append(appid);
        	 sb.append("\",\"ip\": \"172.29.13.40\",\"account\": \"");
        	 sb.append(accountStr);
        	 sb.append("\",\"billLocation\": \"nanjin\",\"billWay\": \"chongzhi\",\"cp\": \"unknown\",\"timestamp\": \"1378262277539\",\"sdk\": \"4.1.2\",\"sdkType\": \"android\"}");
        	 sb.append("\n");
    	 }
    	 writeEncryptMessageToFile(sb.toString());
     }
     
     public void writeEncryptMessageToFile(String request){
    	 FileWriter fw = null;
		try {
			fw = new FileWriter(path.getMessagePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
        BufferedWriter bw = new BufferedWriter(fw); 
        try {
			bw.write(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw.flush();
			bw.flush();
			fw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
}
