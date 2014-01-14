package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.BadBillEntity;

public interface IBadBillBL {
	public boolean createBadBill(BadBillEntity entity);
    public List<BadBillEntity> getAllBadBills();
    public boolean removeRecordsAndEnsureIndex();
    public BadBillEntity getBadBillByUserCode(String userCode);
    public boolean updateBadBillById(BadBillEntity entity);
}
