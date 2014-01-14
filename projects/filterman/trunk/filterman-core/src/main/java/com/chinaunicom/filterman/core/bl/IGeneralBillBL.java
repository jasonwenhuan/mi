package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.GeneralBillEntity;


public interface IGeneralBillBL {
     public boolean createGeneralBill(GeneralBillEntity entity);
     public boolean removeRecordsAndEnsureIndex();
	public List<GeneralBillEntity> getGeneralBillByOffsetAndCount(int offset,
			int count);
}
