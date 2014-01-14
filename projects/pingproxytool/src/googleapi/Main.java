package googleapi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Main {
	public static int times = 0;
	public static List<String> errorList = new ArrayList<String>();
    public static void main(String[] args) throws IOException{
    	GeoCoder gc = new GeoCoder();
    	//String ll = gc.getDataFromGoogle("Canada+Abee");
    	//System.out.println(ll);
    	ReadExcel re = new ReadExcel();
    	//List<String> s = re.getXmlData(re.load("D:/JasonWen/IPromote_Canada_citiess_format.xml"));
    	//List<String> s = re.getXmlData(re.load("D:/JasonWen/a.xml"));
    	List<String> s = re.getXmlData(re.load("C:/Users/wenhuan.HYRON-JS/Desktop/IPromote_Canada_citiess_format.xml"));
    	/*for(int i=0;i<s.size();i++){
    		//System.out.println(s.get(i));
    		String ll = gc.getDataFromGoogle(s.get(i));
    		System.out.println(ll + "----------" + (times++));
    	}*/
    	Main m = new Main();
    	//s = s.subList(0, 100);
    	m.createThreads(1,s);
    	
    	for(int i=0;i<errorList.size();i++){
    		System.out.println(errorList.get(i));
    	}
    }
    
    private void createThreads(int threadSize, List<String> urls) {
		CountDownLatch latch = new CountDownLatch(threadSize);
		int urls4EachThread = urls.size() / threadSize;
		
		/*System.setProperty("http.proxyHost", "172.20.230.5");
        System.setProperty("http.proxyPort", String.valueOf(3128));*/
        
		int i = 0;
		int from = 0;
		int to = 0;
		for (; i < threadSize; i++) {
			from = urls4EachThread * i;
			if (i == threadSize - 1) {
				to = urls.size();
			} else {
				to = urls4EachThread * (i + 1);
			}
			List<String> list = urls.subList(from, to);
			MyThread pup = new MyThread(list);
			//pup.setId(i).setEncoding(encoding);
			//Thread t = new Thread(pup);
			pup.start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		
		
	}
    
    public class MyThread extends Thread{
    	List<String> urls;
    	String successFile;
    	StringBuffer successList = new StringBuffer();
    	public MyThread(List<String> urls) {
    		this.urls = urls;
    		this.successFile = getName() + "_success.txt";
    	}
		@Override
		public void run() {
			GeoCoder gc = new GeoCoder();
			for(int i=0;i<urls.size();i++){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		String ll = null;
	    		String baseDetail = urls.get(i);
				try {
					ll = gc.getDataFromGoogle(baseDetail);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(ll == null){
					System.out.println("error --" + getName());
					errorList.add(baseDetail);
					continue;
				}
	    		String []cityDetail = baseDetail.split(",");
	    		String []latlon = ll.split(";");
	    		String stateName = cityDetail[1].replaceAll("\\+", " ");
	    		String cityName = cityDetail[2].replaceAll("\\+", " ");
	    		String result = cityDetail[0] + "\t" + stateName + "\t" + cityName + "\t" + latlon[0] + "\t" + latlon[1] + "\n";
	    		successList.append(result);
	    		System.out.println(getName() + "----------" + (times++));
	    	}
			
			try {
				write2File(successFile, successList.toString(), "UTF-8");
				System.out.println(getName() + "_write success file success");

			} catch (Exception e) {
			}
		}
    	
    }
    
    public static void write2File(String fileName, String content, String charset) {
        if (fileName == null || fileName.equals("") || content == null || content.equals("")) {
			return;
		}
		Writer writer = null;
		try {
			if(charset == null){
				charset = "UTF-8";
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
    
}
