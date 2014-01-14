package com.chinaunicom.filterman.ws.util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.bl.IBadBillBL;
import com.chinaunicom.filterman.core.bl.impl.BadBillBL;
import com.chinaunicom.filterman.core.db.entity.BadBillEntity;
import com.csvreader.CsvReader;

public class ImportBadBillDataToMongo {
	
	protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");
	
	@Autowired
    private PathBean pathBean = (PathBean)ctx.getBean("pathBean");
	
	@Autowired
    private IBadBillBL badBillBL = (BadBillBL)ctx.getBean("badBillBL");
	
	public int currentRecord = 1;
	
	public Map<String, Integer> samePhoneCache = new HashMap<String, Integer>();
	
	public static void main(String[] args){
		Long start = System.currentTimeMillis();
		ImportBadBillDataToMongo importer = new ImportBadBillDataToMongo();
		importer.removeRecordAndEnsureIndex();
		importer.importData();
		Long end = System.currentTimeMillis();
		System.out.println("Total cost " + (end - start) + "ms");
	}
	
	public void removeRecordAndEnsureIndex(){
		badBillBL.removeRecordsAndEnsureIndex();
	}
	
	public void importData(){
		
		CsvReader reader = null;
		String[] str;
		BadBillEntity entity = new BadBillEntity();
		Long startTime = 0l;
		Long endTime = 0l;
		BadBillEntity myEntity = null;
		Integer originalFee = null;
		try {
			reader = new CsvReader(pathBean.getCsvBadBillPath());
			reader.readHeaders();
			while(reader.readRecord()){
				 str = reader.getValues();
				 if (str != null && str.length > 0) {
					 entity.setUserCode(str[0]);
					 entity.setCityCode(str[1]);
					 entity.setUserType(str[2]);
					 entity.setComCode(str[3]);
					 entity.setComType(str[4]);
					 entity.setComeinCode(str[5]);
					 entity.setVacType(str[6]);
					 entity.setVacCode(str[7]);
					 entity.setFee(str[8]);
					 entity.setId(null);
					 
					 
					startTime = System.currentTimeMillis();
					myEntity = badBillBL.getBadBillByUserCode(str[0]);
					if(myEntity != null){
						originalFee = Integer.parseInt(myEntity.getFee());
						originalFee += Integer.parseInt(str[8]);
						myEntity.setFee(String.valueOf(originalFee));
						badBillBL.updateBadBillById(myEntity);
					}else{
						badBillBL.createBadBill(entity);
					}
					endTime = System.currentTimeMillis();
				 }
				System.out.println("have import badbill  " + currentRecord + "  cost " + (endTime - startTime));
				currentRecord++;
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	    
	}
}
