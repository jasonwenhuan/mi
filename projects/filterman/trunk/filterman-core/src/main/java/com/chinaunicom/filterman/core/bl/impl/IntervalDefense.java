package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.db.dao.BlockphoneDao;
import com.chinaunicom.filterman.core.db.dao.IntervalHistoryDao;
import com.chinaunicom.filterman.core.db.dao.IntervalMstDao;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.BlockedEntity;
import com.chinaunicom.filterman.core.db.entity.IntervalHistoryEntity;
import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public class IntervalDefense implements IDefenseBL{
    public final static String POLICY_NAME = "支付间隔策略";

    private final static String EXP_BLOCK_INTERVAL = "触发[" + POLICY_NAME +
            "]. 手机号[%s]在预定支付间隔时间[%s]时内,当前累计支付总额[%s]元以超出预定总额[%s]元.";

    @Autowired
    private IntervalHistoryDao intervalHistoryDao;

    @Autowired
    private IntervalMstDao intervalDao;

    @Autowired
    private BlockphoneDao blockPhoneDao;

	@Override
	public boolean execute(RequestEntity requestEntity) throws PolicyException,
			RequestException {

		if(!StringUtils.hasText(requestEntity.getPhone())){
            throw new RequestException("The phone is blank.", requestEntity);
		}
		
		//add to interval history
        intervalHistoryDao.saveIntervalEntity(createIntervalHistoryEntity(requestEntity));

		int totalBill = 0;
        Date startDate = null;
        List<IntervalMstEntity> intervals = intervalDao.getAllIntervals();
		for(IntervalMstEntity intervalEntity : intervals){
			startDate = FiltermanUtils.getStartDate(requestEntity.getTimestamp(), intervalEntity.getInterval());
			totalBill = intervalHistoryDao.getBillTotal(requestEntity.getPhone(), startDate, requestEntity.getTimestamp());

			if(totalBill > intervalEntity.getLimitTotal()){
				addThePhoneToBlockTable(requestEntity.getPhone(), requestEntity.getTimestamp());

				throw new PolicyException(
                        String.format(EXP_BLOCK_INTERVAL,
                                requestEntity.getPhone(),
                                intervalEntity.getInterval(),
                                totalBill/100,
                                intervalEntity.getLimitTotal()/100));
			}
		}

		return true;
	}
	
	private void addThePhoneToBlockTable(String phone, Date date){
        BlockedEntity entity = blockPhoneDao.getBlockByPhone(phone);
		if(entity != null){
            blockPhoneDao.updateBlockByPhone(phone, date);
		}else{
			entity = FiltermanUtils.createBlockEntity(date, phone, POLICY_NAME);
            blockPhoneDao.saveBlockPhoneEntity(entity);
		}
	}
	
	private static IntervalHistoryEntity createIntervalHistoryEntity(RequestEntity request) {
		IntervalHistoryEntity entity = new IntervalHistoryEntity();

        entity.setPhone(request.getPhone());
        entity.setBill(Integer.parseInt(request.getPayfee()));
		entity.setCreateDate(request.getTimestamp());

        return entity;
	}

    @Override
    public String toString() {
        return POLICY_NAME;
    }

}
