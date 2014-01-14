package com.hyron.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileConvertManager {
	private String basePath;
    public String convertHtmlToString(String path,String encoding){
    	try{
        	File f = new File(basePath+path);  
            FileInputStream is = new FileInputStream(f);  
            BufferedInputStream bis = new BufferedInputStream(is);  
          
            ByteArrayOutputStream fos = new ByteArrayOutputStream();  
            byte buffer[] = new byte[2048];  
  
            int read;  
            do {  
                read = is.read(buffer, 0, buffer.length);  
                if (read > 0) {   
                    fos.write(buffer, 0, read);   
                }  
            } while (read > -1);  
  
            fos.close();  
            bis.close();  
            is.close();  
            return fos.toString(encoding); 
    	}catch(Exception e){
    		return null;
    	}
    }
   /* public static void main(String[] args) throws IOException{
    	String s = convertHtmlToString("postinformation.html","UTF-8");
    	System.out.println(s);
    }*/
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
