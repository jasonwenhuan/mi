package com.chinaunicom.filterman.core.bl.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.BaseTest;
import com.chinaunicom.filterman.core.bl.IBadBillBL;
import com.chinaunicom.filterman.core.db.entity.BadBillEntity;

public class BadBillBLTest extends BaseTest{
	@Autowired
    private IBadBillBL badBillBL = (IBadBillBL)ctx.getBean("badBillBL");
	
	@Test
	public void testCreateBadBill(){
		BadBillEntity bad1 = new BadBillEntity();
		bad1.setCityCode("113");
		bad1.setComCode("91639");
		bad1.setComeinCode("2100079414");
		bad1.setComType("21");
		bad1.setFee("900");
		bad1.setUserCode("18648202968");
		bad1.setUserType("0");
		bad1.setVacCode("2100079414");
		bad1.setVacType("21000794");
		
		badBillBL.createBadBill(bad1);
	}
}
