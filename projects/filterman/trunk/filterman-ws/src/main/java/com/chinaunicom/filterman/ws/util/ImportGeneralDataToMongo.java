package com.chinaunicom.filterman.ws.util;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import com.chinaunicom.filterman.core.bl.IZonemapphoneBL;
import com.chinaunicom.filterman.core.bl.impl.FiltermanUtils;
import com.chinaunicom.filterman.core.bl.impl.ZonemapphoneBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaunicom.filterman.core.bl.IGeneralBillBL;
import com.chinaunicom.filterman.core.bl.impl.GeneralBillBL;
import com.chinaunicom.filterman.core.db.entity.GeneralBillEntity;
import com.csvreader.CsvReader;

public class ImportGeneralDataToMongo {
	protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");
	
	@Autowired
    private PathBean pathBean = (PathBean)ctx.getBean("pathBean");
	
	@Autowired
    private IGeneralBillBL generalBillBL = (GeneralBillBL)ctx.getBean("generalBillBL");

    @Autowired
    private IZonemapphoneBL zonemapphoneBL = (ZonemapphoneBL)ctx.getBean("zoneMapPhoneBL");

	public int currentRecord = 1;
	
	public static void main(String[] args){
		ImportGeneralDataToMongo importer = new ImportGeneralDataToMongo();
		importer.removeRecordAndEnsureIndex();
		importer.importData();
	}
	
	public void removeRecordAndEnsureIndex(){
		generalBillBL.removeRecordsAndEnsureIndex();
	}
	
	public void importData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		CsvReader reader = null;
		String[] str;
		GeneralBillEntity entity = new GeneralBillEntity();
		try {
			reader = new CsvReader(pathBean.getCsvFilePath());
			reader.readHeaders();
            StringBuffer sb = null;
			while(reader.readRecord()){
				str = reader.getValues();
				 if (str != null && str.length > 0) {
                     sb = new StringBuffer("");
					 //entity = new GeneralBillEntity();
					 entity.setUserCode(str[0]);
					 entity.setMessage(str[1]);
                     entity.setZoneCode(zonemapphoneBL.getZoneCodeWithPhone(FiltermanUtils.getPrePhone(str[0])));
					 entity.setFee(Integer.parseInt(str[2]));
                     sb.append(str[3].substring(0,4));
                     sb.append("-");
                     sb.append(str[3].substring(4,6));
                     sb.append("-");
                     sb.append(str[3].substring(6,8));
                     sb.append(" ");
                     sb.append(str[3].substring(8,10));
                     sb.append(":");
                     sb.append(str[3].substring(10,12));
                     sb.append(":");
                     sb.append(str[3].substring(12,14));

					 entity.setCreateDate(dateFormat.parse(sb.toString()));

					generalBillBL.createGeneralBill(entity);
				 }

				currentRecord++;
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

        System.out.println("have import " + currentRecord);
	}
}
