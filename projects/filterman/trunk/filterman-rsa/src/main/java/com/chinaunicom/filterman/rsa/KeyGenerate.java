package com.chinaunicom.filterman.rsa;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.PublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.rsa.bean.Path;


public class KeyGenerate {
	
	@Autowired
	private Path path;
	
	public String getPublicKey(){
		Key filePublicKey = null;
		try {
			filePublicKey = readPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
        String pk = bytesToString(filePublicKey.getEncoded());
        
		return pk;
    }
    
    public PublicKey readPublicKey() throws Exception{
   	 ObjectInputStream ois = null;
        try {
           	ois = new ObjectInputStream(new FileInputStream(path.getPublicKeyPath()));
            return (PublicKey) ois.readObject();
        } catch (Exception e) {
            throw e;
        }
        finally{
            ois.close();
        }
   }
    
	public byte[] readFileAsByteArray() {
		File file = new File(path.getPublicKeyPath());
		byte fileContent[] = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			fileContent = new byte[(int) file.length()];
			fin.read(fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileContent;
	}
	
	@SuppressWarnings("restriction")
	public void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = path.getPublicKeyPath();
		String downLoadPath = ctxPath;

		long fileLength = new File(downLoadPath).length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		try {
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
    
    private String bytesToString(byte[] encrytpByte) {
    	String result = "";
    	for (Byte bytes : encrytpByte) {
    	    result += bytes.toString() + " ";
    	}
    	return result;
    }


	public Path getPath() {
		return path;
	}


	public void setPath(Path path) {
		this.path = path;
	}
}
