package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.dao.BlockphoneDao;
import com.chinaunicom.filterman.core.db.dao.ZoneMapPhoneDao;
import com.chinaunicom.filterman.core.db.dao.ZonePayDailySumDao;
import com.chinaunicom.filterman.core.db.dao.ZonePayHistoryDao;
import com.chinaunicom.filterman.core.db.dao.ZonePayRuleDao;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.core.db.entity.ZonePayDailySumEntity;
import com.chinaunicom.filterman.core.db.entity.ZonePayHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: larry
 */

public class ZonePayDefense implements IDefenseBL {
    public final static String POLICY_NAME = "区域比重策略";

    private final static String EXP_ZONE_PAY = "触发了[" + POLICY_NAME + "], " +
            "号码[%s]所属区域[%s]已超出额度限制，当前消费额度比率[%s]%较历史比率[%s]%大于预定比率[%s]%.";

    @Autowired
    private ZoneMapPhoneDao zoneMapPhoneDao;

    @Autowired
    private ZonePayDailySumDao zonePayDailySumDao;

    @Autowired
    private ZonePayHistoryDao zonePayHistoryDao;

    @Autowired
    private ZonePayRuleDao zonePayRuleDao;

    @Autowired
    private BlockphoneDao blockPhoneDao;

    private static SimpleDateFormat bigDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000z");
    private static Calendar calendar = Calendar.getInstance();

    @Override
    public boolean execute(RequestEntity request) throws RequestException, PolicyException {
        boolean isFine = false;

        if(!StringUtils.hasText(request.getPhone())) {
            throw new RequestException("The phone is blank.", request);
        }

        Date bigDate = getBigDate(request.getTimestamp());
        Date startDate = getStartTime(request.getTimestamp());
        String prePhone = FiltermanUtils.getPrePhone(request.getPhone());
        String zoneCode = zoneMapPhoneDao.getZoneCodeWithPhone(prePhone);

//        ZonePayHistoryEntity payHistoryEntity = new ZonePayHistoryEntity();
//        payHistoryEntity.setBill(Integer.parseInt(request.getPayfee()));
//        payHistoryEntity.setCreateDate(request.getTimestamp());
//        payHistoryEntity.setPhone(request.getPhone());
//        payHistoryEntity.setZoneCode(zoneCode);
        zonePayHistoryDao.saveEntity(
                createPayHistoryEntity(
                        Integer.parseInt(request.getPayfee()),
                        request.getTimestamp(),
                        prePhone,
                        zoneCode));

        ZonePayDailySumEntity dailySumEntity = zonePayDailySumDao.getEntityWithZone(zoneCode, bigDate);
        if (dailySumEntity == null) return isFine;

        int totalBill = zonePayHistoryDao.getTotalBill(zoneCode, startDate, request.getTimestamp());
        int totalBillForZone = zonePayHistoryDao.getTotalBillForZone(zoneCode, startDate, request.getTimestamp());
        int limitRate = zonePayRuleDao.getLimitRateWithLevel(FiltermanUtils.getRateLevel(dailySumEntity.getZoneRate()));
        float nowRate = (totalBillForZone + dailySumEntity.getZoneSum()) / (totalBill + dailySumEntity.getDailySum());
        if ((nowRate - dailySumEntity.getZoneRate()) >= limitRate) {
            blockPhoneDao.saveZonePayBlock(
                    FiltermanUtils.createBlockEntity(
                            request.getTimestamp(),
                            request.getPhone(),
                            POLICY_NAME));

            throw new PolicyException(
                    String.format(EXP_ZONE_PAY,
                            request.getPhone(),
                            zoneCode,
                            nowRate,
                            dailySumEntity.getZoneRate(),
                            limitRate));
        }

        return isFine;
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }

    private static Date getBigDate(Date requestDt) {
        Date date = null;

        calendar.setTime(requestDt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String dt = null;
        if (hour > 2) {
            dt = bigDateFormat.format(new Date(requestDt.getTime() - 24 * 60 * 60 * 1000));
        } else {
            dt = bigDateFormat.format(new Date(requestDt.getTime() - 26 * 60 * 60 * 1000));
        }

        try {
            date = bigDateFormat.parse(dt);
        } catch (Exception e) {
            new RequestException("Doing ZonePayDefense.getBigDate error occurred on date [" + dt + "].");
        }

        return date;
    }

    private static Date getStartTime(Date requestDt) throws RequestException {
        Date dt = null;

        String date = null;
        calendar.setTime(requestDt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 2) {
            date = startDateFormat.format(requestDt);
        } else {
            date = startDateFormat.format(new Date(requestDt.getTime() - 2 * 60 * 60 * 1000));
        }

        try {
            dt = startDateFormat.parse(date);
        } catch (Exception e) {
            new RequestException("Doing ZonePayDefense.getStartTime error occurred on date [" + date + "].");
        }

        return dt;
    }

    private static ZonePayHistoryEntity createPayHistoryEntity(
            int bill,
            Date date,
            String prePhone,
            String zoneCode
    ) {
        ZonePayHistoryEntity entity = new ZonePayHistoryEntity();
        entity.setBill(bill);
        entity.setCreateDate(date);
        entity.setPhone(prePhone);
        entity.setZoneCode(zoneCode);
        return entity;
    }
}
