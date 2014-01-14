package com.chinaunicom.filterman.ws.util;

import java.io.File;
import java.util.List;

import javax.ws.rs.core.MediaType;


import com.chinaunicom.filterman.core.db.dao.ZonePayHistoryDao;
import com.chinaunicom.filterman.utilities.Logging;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.bl.IGeneralBillBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.bl.impl.BlockphoneBL;
import com.chinaunicom.filterman.core.bl.impl.GeneralBillBL;
import com.chinaunicom.filterman.core.bl.impl.IntervalHistoryBL;
import com.chinaunicom.filterman.core.bl.impl.RelatedPhoneBL;
import com.chinaunicom.filterman.core.db.entity.GeneralBillEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import org.springframework.util.FileCopyUtils;

public class PolicyDefenseTest{
	
	 protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");

     public String rootPath = "http://localhost:8080/filterman";

     public static ApacheHttpClient4 client;

     public WebResource wr;

     public ClientResponse clientResponse;
    
	 @Autowired
     private RelatedPhoneBL relatedPhoneBL = (RelatedPhoneBL)ctx.getBean("relatedPhoneBL");
	 
	 @Autowired
     private IntervalHistoryBL intervalHistoryBL = (IntervalHistoryBL)ctx.getBean("intervalHistoryBL");
	 
	 @Autowired
     private BlockphoneBL blockPhoneBL = (BlockphoneBL)ctx.getBean("blockPhoneBL");
	 
	 @Autowired
     private ZonePayHistoryDao zonePayHistoryDao = (ZonePayHistoryDao)ctx.getBean("zonePayHistoryDao");

     @Autowired
     private IGeneralBillBL generalBillBL = (GeneralBillBL)ctx.getBean("generalBillBL");

     @Autowired
     private PathBean pathBean = (PathBean)ctx.getBean("pathBean");

	 public int allCount = 1;
	 
	 public int allThreadCount = 0;
	 
	 public static int currentThreadCount = 0;
	 
	 public boolean doExcute = true;
	 
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
         int skipCount = policyDefenseTest.getDoneCount();
         policyDefenseTest.removeHistoryRecords(skipCount);
	     policyDefenseTest.doTest(skipCount);
     }

    private void removeHistoryRecords(int count) {
        if (count > 0) return;

        relatedPhoneBL.removeRecordsAndEnsureIndex();
        intervalHistoryBL.removeRecordsAndEnsureIndex();
        blockPhoneBL.removeRecordsAndEnsureIndex();
        zonePayHistoryDao.removeRecordsAndEnsureIndex();
    }

    private void doTest(int skipCount) {
        int offset = skipCount;
        try {
            while (true) {
                List<GeneralBillEntity> entities = generalBillBL.getGeneralBillByOffsetAndCount(offset, 10000);
                if (entities == null) break;

                for (GeneralBillEntity gbEntity : entities) {
                    try {
                        callPayCheck(createEntity(gbEntity));
                    } catch (PolicyException e) {
                    } catch (RequestException e) {
                    }

                    offset++;
                }
            }
        } catch (Exception e) {
            Logging.logError(">>>>PolicyDefenseTest.doTest error occurred.", e);
        } finally {
            this.saveDoneCount(offset);
        }
    }

    private int getDoneCount() {
        int count = 0;
        try {
            File file = new File(pathBean.getDefenseTestPath());

            String cont = FileUtils.readFileToString(file);
            if (cont != null && !"".equals(cont)) {
                count = Integer.parseInt(cont);
            }
        } catch (Exception e) {
            Logging.logError(">>>read file error occurred.", e);
        }

        return count;
    }

    private void saveDoneCount(int count) {
        try {
            File file = new File(pathBean.getDefenseTestPath());

            FileUtils.writeStringToFile(file, String.valueOf(count));
        } catch (Exception e) {
            Logging.logTransaction(">>>>>>>" + count);
        }
    }

    private static RequestEntity createEntity(GeneralBillEntity gbEntity) {
        RequestEntity entity = new RequestEntity();
        entity.setPhone(gbEntity.getUserCode());
        entity.setOrderid(gbEntity.getMessage());
        entity.setPayfee(String.valueOf(gbEntity.getFee()));
        entity.setTimestamp(gbEntity.getCreateDate());
        entity.setAppid("doTest");
        entity.setAppName("doTest");

        return entity;
    }
/*
    private void excute(){
    	 int limitThreadNumber = 10;
	     int recordsEveryThread = 10000;
	     
	     Long startTime = System.currentTimeMillis();
	     int offset = 0;
	     DefenseUT td = null;
    	 try {
	    	while(doExcute){
	    		 synchronized (DefenseUT.class) {
	                 while (currentThreadCount >= limitThreadNumber) {  
	                     DefenseUT.class.wait();  
	                 }  
	                 
	                 offset = allThreadCount * recordsEveryThread;
	                 td = new DefenseUT(String.valueOf(allThreadCount+1), offset, recordsEveryThread); 
	                
	                 td.start();  
	                 allThreadCount ++;
	                 currentThreadCount ++;
	                 
	    		 }
	    	}
		 }catch(InterruptedException e){
			 e.printStackTrace();
		 }finally{
			 Long endTime = System.currentTimeMillis();
			 System.out.println("All Thread Finish, Cost " + (endTime - startTime) + "ms");
		 }
	 }
	 
	 class DefenseUT extends Thread{
		private int offset;
		private int recordsEveryThread;
		
		private String threadName;
		
		public DefenseUT(String threadName,int offset, int recordsEveryThread){
			this.threadName = threadName;
			this.offset = offset;
			this.recordsEveryThread = recordsEveryThread;
		}

		public void run() {
			 Long startTime = null;
			 Long endTime = null;
			 RequestEntity entity = null;
			 int i=0;
			 try {
			    List<GeneralBillEntity> entities = generalBillBL.getGeneralBillByOffsetAndCount(offset, 
			    		recordsEveryThread);
				for (GeneralBillEntity gbEntity : entities) {
					 entity = new RequestEntity();
					 entity.setPhone(gbEntity.getUserCode());
					 entity.setOrderid(gbEntity.getMessage());
					 entity.setPayfee(String.valueOf(gbEntity.getFee()));
					 entity.setTimestamp(gbEntity.getCreateDate());
					 try {
						callPayCheck(entity);
					} catch (PolicyException e) {
					} catch (RequestException e) {
					}

					allCount++;
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				synchronized (DefenseUT.class) {  
					currentThreadCount--;  
					DefenseUT.class.notifyAll();  
	            }  
			}
		}

		public String getThreadName() {
			return threadName;
		}

		public void setThreadName(String threadName) {
			this.threadName = threadName;
		}
	 }
*/
	 private void callPayCheck(RequestEntity entity) throws PolicyException, RequestException{
		 
		 clientResponse = wr.path("/pay/check4test")
         .type(MediaType.APPLICATION_JSON_TYPE)
         .accept(MediaType.APPLICATION_JSON_TYPE)
         .post(ClientResponse.class, entity);

		 clientResponse.getEntity(new GenericType<String>(){});
		 
		 //System.out.println(result);
	 }
	 
	 
}
