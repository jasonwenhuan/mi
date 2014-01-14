package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileUtil {

	public static final int INT_1024 = 1024;
	
	public static String getNameFromPath(String path){
    	String fileName = "";
    	try{
        	File file = new File(path);
        	if(file != null){
        		fileName = file.getName();
        	}
    	}catch(Exception ex){
    		return fileName;
    	}

    	return fileName;
    }
	
	public static String[] getAllFilesWithInSubFolder(String path, final String file2Include){
		String[] list;
		File filePath = new File(path);
		if(file2Include == null || file2Include.trim().length() == 0){
			list = filePath.list();
		}else{
			list = filePath.list(new FilenameFilter(){
				public boolean accept(File dir, String name) {
					Pattern pattern = Pattern.compile(file2Include);
					return pattern.matcher(name).matches();
				}
			});
		}
		
		return list;
	}
	
	public static File[] getAllFilesWithInSubFolderAsFileArray(File dir, final String file2Include){
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Pattern pattern = Pattern.compile(file2Include);
				return pattern.matcher(name).matches();
			}
		});
	}
	
	public static File[] getAllFilesWithInSubFolderAsFileArray(String path, final String file2Include){
		return getAllFilesWithInSubFolderAsFileArray(new File(path),file2Include);
	}
	
	public static void getAllFilesWithInSubFolder(String path, List<String> allFiles){
		File dir = new File(path); 
        File[] files = dir.listFiles(); 
        
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
            	getAllFilesWithInSubFolder(files[i].getAbsolutePath(),allFiles); 
            } else { 
                allFiles.add(files[i].getAbsolutePath());                    
            } 
        } 
	}
	
	public static List<String> filterFilesByExtension(List<String> orgFiles,String[] extensionArr){
		List<String> newFiles = new ArrayList<String>();
		
		for(String orgFile: orgFiles){
			if(!matchExtension(getExtension(orgFile),extensionArr)){
				newFiles.add(orgFile);
			}
		}
		
		return newFiles;
	}
	
	public static String getExtension(String fileName){
		if(fileName == null || fileName.equals("")){
			return "";
		}
		String[] token = fileName.split("\\.");
		return token[token.length-1];
	}
	
	public static boolean matchExtension(String extension2Match, String[] extensionArr){
		if(extension2Match == null || extension2Match.equals("") || extensionArr == null || extensionArr.length == 0){
			return false;
		}
		for(int i=0;i<extensionArr.length;i++){
			if(extensionArr[i].toLowerCase().equals(extension2Match.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	// Read File
	public static void readFileByBytes(String fileName){
		File file = new File(fileName);
		InputStream in = null;
		BufferedInputStream bis = null;
		
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytes = new byte[INT_1024];
			int length = 0;
			
			while((length = bis.read(bytes)) != -1){
				System.out.write(bytes, 0, length);
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void readFileByChars(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int oneChar;
			while((oneChar = reader.read()) != -1){
				if(((char)oneChar) != '\r'){
					System.out.print((char) oneChar);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readFileByLines(String fileName, String charset) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
        	//reader = new BufferedReader(new FileReader(file));
			if(charset == null){
				charset = HttpClientConstants.CHARSET_UTF8;
			}
        	reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
            String oneLine = null;
            List<String> retString = new ArrayList<String>();
            while ((oneLine = reader.readLine()) != null) {
                retString.add(oneLine);
            }
            reader.close();
            return retString;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return null;
    }
	
	public static long getAvailableBytes(InputStream in){
			try {
				return in.available();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
	}
	
	// Write File
	public static void writeFileByBytes(String fileName, String content, String charset){
		if(content == null || content.length() == 0) return;
		
		byte[] bytes;
		try {
			bytes = content.getBytes(charset);
			writeFileByBytes(fileName,bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeFileByBytes(String fileName, byte[] bytes){
		if(bytes == null || bytes.length == 0) return;
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void writeFileByBytes(String fileName, InputStream is){
		if(is == null) return;
		
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		
		try {
			fos = new FileOutputStream(fileName);
			byte bytes[] = new byte[INT_1024];
			
			int readSize = 0;
			bis = new BufferedInputStream(is);
			while((readSize = bis.read(bytes)) != -1){
				fos.write(bytes, 0, readSize);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void write2File(String fileName, String content, String charset) {
        if (fileName == null || fileName.equals("") || content == null || content.equals("")) {
			return;
		}
		Writer writer = null;
		try {
			if(charset == null){
				charset = HttpClientConstants.CHARSET_UTF8;
			}
			writer = new OutputStreamWriter(new FileOutputStream(fileName,true), charset);

			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	// Copy File
	public static void copyFile(String org, String dst, boolean useBuffer){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			fis = new FileInputStream(org);
			fos = new FileOutputStream(dst);
			bos = new BufferedOutputStream(new FileOutputStream(dst));
			int length = 0;
			byte[] bytes = new byte[INT_1024];
			while((length = fis.read(bytes)) != -1){
				if(useBuffer){
					bos.write(bytes, 0, length);
				}else{					
					fos.write(bytes, 0, length);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
			
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
	
}
