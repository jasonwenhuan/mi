package com.chinaunicom.filterman.core.bl.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.BaseTest;
import com.chinaunicom.filterman.core.db.dao.IntervalMstDao;
import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;

public class IntervalDaoTest extends BaseTest{
	@Autowired
    private IntervalMstDao intervalDao = (IntervalMstDao)ctx.getBean("intervalDao");
	
	@Test
	public void testCreateInterval(){
		/*IntervalMstEntity intervalEntity  = new IntervalMstEntity();
		intervalEntity.setInterval(1);
		intervalEntity.setLimitTotal(50);*/
		
		IntervalMstEntity intervalEntity1  = new IntervalMstEntity();
		intervalEntity1.setInterval(2);
		intervalEntity1.setLimitTotal(100);
		
		/*intervalDao.saveIntervalEntity(intervalEntity);*/
		intervalDao.saveIntervalEntity(intervalEntity1);
	}
}
