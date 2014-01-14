package com.chinaunicom.filterman.ws;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.MediaType;


import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.ws.util.PathBean;
import com.csvreader.CsvReader;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;

public class PolicyDefenseTest{
	
	 protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");

     public String rootPath = "http://localhost:8080/filterman-ws";

     public static ApacheHttpClient4 client;

     public WebResource wr;

     public ClientResponse clientResponse;
    
	 /*@Autowired
     private IDefenseBL intervalDefense = (IDefenseBL)ctx.getBean("intervalDefense");
	 
	 @Autowired
     private IDefenseBL relatedPhone = (IDefenseBL)ctx.getBean("relatedPhoneDefense");*/
	 
	 @Autowired
     private PathBean pathBean = (PathBean)ctx.getBean("pathBean");
	 
	 public int allCount = 1;
	 
	 public PolicyDefenseTest(){
         ClientConfig clientConfig = new DefaultApacheHttpClient4Config();
         clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
	                Boolean.TRUE);
         clientConfig.getProperties().put(
	                ApacheHttpClient4Config.PROPERTY_DISABLE_COOKIES, false);
         final ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();

         // TODO just to avoid deadlock
         threadSafeClientConnManager.setDefaultMaxPerRoute(100);
         threadSafeClientConnManager.setMaxTotal(1000);

         clientConfig.getProperties().put(
                ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER,
                threadSafeClientConnManager);
         client = ApacheHttpClient4.create(clientConfig);
         client.getClientHandler().getHttpClient().getParams()
                .setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, false);
         wr = client.resource(rootPath);
	 }
	 
	 public static void main(String[] args){
		 PolicyDefenseTest policyDefenseTest = new PolicyDefenseTest();
		 policyDefenseTest.excute();
	 }
	 
	 private void excute(){
		
	     CsvReader reader = null;
	     int recordsEveryThread = 100000;
	     int threadNumber = 1;
	     int totalRecords = 0;
	     int myRecords = 2;
	     CountDownLatch latch = new CountDownLatch(threadNumber);
	     ExecutorService pool = Executors.newFixedThreadPool(50);
    	 try {
			reader = new CsvReader(pathBean.getCsvFilePath());
		    reader.readHeaders();
		    long startTime=System.currentTimeMillis(); 
		    while(reader.readRecord()){
		    	totalRecords ++;
		    }
		    long endTime=System.currentTimeMillis(); 
	    	System.out.println(totalRecords + ">>>" + (endTime - startTime));
	    	
	    	if(myRecords == 0){
	    		myRecords = totalRecords;
	    	}
	    	
	    	recordsEveryThread = myRecords / threadNumber;
	    	
	    	for(int i=0; i< threadNumber; i++){
	    		CsvReader myReader = new CsvReader(pathBean.getCsvFilePath());
	    		myReader.readHeaders();
	    		int offset = i * recordsEveryThread;
	    		for(int j=0; j < offset; j++){
	    			myReader.readRecord();
	    		}
	    		if(i == threadNumber - 1){
	    			recordsEveryThread = myRecords - offset;
	    		}
	    		String threadName = "" + i;
	    		Thread dt = new DefenseUT(myReader, recordsEveryThread, threadName, latch);
	    		pool.execute(dt);
	    		//dt.start();
	    	}
	    	
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
		 }catch(IOException e){
			 e.printStackTrace();
		 }finally{
			pool.shutdown();
		    try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("All Thread Finish");
		 }
	 }
	 
	 class DefenseUT extends Thread{
		private CsvReader reader;
		private int recordsEveryThread;
		private String threadName;
		private CountDownLatch latch;
		
		public DefenseUT(CsvReader reader, int recordsEveryThread, String threadName, CountDownLatch latch){
			this.reader = reader;
			this.recordsEveryThread = recordsEveryThread;
			this.threadName = threadName;
			this.latch = latch;
		}

		public void run() {
			 int i=1;
			 Long startTime = null;
			 Long endTime = null;
			 try {
				while (reader.readRecord()) {
					if(i > recordsEveryThread){
						break;
					}
					 String[] str = reader.getValues();
					 if (str != null && str.length > 0) {
						 RequestEntity entity = new RequestEntity();
						 entity.setPhone(str[0]);
						 entity.setOrderid(str[1]);
						 entity.setPayfee(str[2]);
						 
						 String timestamp = str[3];
						 if(timestamp == null){
							 entity.setTimestamp(new Date());
						 }else{
							 int year = Integer.parseInt(timestamp.substring(0, 4));
							 int month = Integer.parseInt(timestamp.substring(4, 6));
							 int day = Integer.parseInt(timestamp.substring(6, 8));
							 int hour = Integer.parseInt(timestamp.substring(8, 10));
							 int minute = Integer.parseInt(timestamp.substring(10, 12));
							 int second = Integer.parseInt(timestamp.substring(12, 14));
							 Calendar cal = Calendar.getInstance();
							 cal.set(year, month, day, hour, minute, second);
							 
							 entity.setTimestamp(cal.getTime());
						 }
						 
						 try {
							startTime = System.currentTimeMillis();
							callPayCheck(entity);
							endTime = System.currentTimeMillis();
						} catch (PolicyException e) {
							endTime = System.currentTimeMillis();
							//endTime = System.currentTimeMillis();
						} catch (RequestException e) {
							endTime = System.currentTimeMillis();
						}
					 }
					 System.out.println("excute>>> Thread:" + threadName + ">>>" + i+" times" + "    " +
					 		"all>>>>" + allCount + "    >>>>costtime " + (endTime - startTime));
					 i++;
					 allCount++;
				 }
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(reader != null){
					reader.close();
				}
				latch.countDown();
			}
		}
	 }
	 
	 private void callPayCheck(RequestEntity entity) throws PolicyException, RequestException{
		 
		 clientResponse = wr.path("/pay/checkForTest")
         .type(MediaType.APPLICATION_JSON_TYPE)
         .accept(MediaType.APPLICATION_JSON_TYPE)
         .post(ClientResponse.class, entity);

		 String result = clientResponse.getEntity(new GenericType<String>(){});
		 
		 System.out.println(result);
	 }
}
