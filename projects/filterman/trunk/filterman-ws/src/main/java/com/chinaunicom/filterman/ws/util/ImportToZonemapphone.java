package com.chinaunicom.filterman.ws.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.chinaunicom.filterman.core.bl.IZonemapphoneBL;
import com.chinaunicom.filterman.core.bl.impl.ZonemapphoneBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.db.entity.ZoneMapPhoneEntity;
import com.csvreader.CsvReader;

public class ImportToZonemapphone {
	protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");
	
	@Autowired
    private PathBean pathBean = (PathBean)ctx.getBean("pathBean");
	
	@Autowired
    private IZonemapphoneBL zoneMapPhoneBL = (ZonemapphoneBL)ctx.getBean("zoneMapPhoneBL");
	
	public int currentRecord = 0;
	
	public static void main(String[] args){
        ImportToZonemapphone importer = new ImportToZonemapphone();
		importer.removeRecordAndEnsureIndex();
		importer.importData();
	}
	
	public void removeRecordAndEnsureIndex(){
		zoneMapPhoneBL.removeRecordsAndEnsureIndex();
	}
	
	public void importData(){
		
		CsvReader reader = null;
		String[] str;
		ZoneMapPhoneEntity entity = new ZoneMapPhoneEntity();
		String startPrePhone = null;
		String endPrePhone = null;
		Integer startIntPhone = null;
		Integer endIntPhone = null;
		try {
			reader = new CsvReader(new InputStreamReader(new FileInputStream(new File(pathBean.getZonemapphonePath())),"UTF-8"));
			reader.readHeaders();
			while(reader.readRecord()){
				str = reader.getValues();
				 if (str != null && str.length > 3) {
				     entity.setZoneName(str[0]);
                     entity.setZoneCode(str[1]);
				     startPrePhone = str[2];
				     endPrePhone = str[3];
				     if(startPrePhone.equalsIgnoreCase(endPrePhone)){
				    	 entity.setPrePhone(startPrePhone);
				    	 zoneMapPhoneBL.createZonemapphone(entity);

                         currentRecord++;
				     }else{
				    	 startIntPhone = Integer.parseInt(startPrePhone);
				    	 endIntPhone = Integer.parseInt(endPrePhone);
				    	 for(int i = startIntPhone; i <= endIntPhone; i++){
				    		 entity.setPrePhone(String.valueOf(i));
				    		 zoneMapPhoneBL.createZonemapphone(entity);

                             currentRecord++;
				    	 }
				     }
				 } else {
                     System.out.println(">>>>> " + str[0] + ">>>>>" + str[1]);
                 }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

        System.out.println("Total count:" + currentRecord);

    }
}
