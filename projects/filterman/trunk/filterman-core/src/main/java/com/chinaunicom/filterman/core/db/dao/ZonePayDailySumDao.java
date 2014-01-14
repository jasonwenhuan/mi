package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.ZonePayDailySumEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * User: larry
*/

public class ZonePayDailySumDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public boolean createZonePayDailySum(ZonePayDailySumEntity entity) {
        boolean isFine = false;
        
        try {
        	mongoTemplate.save(entity, Constants._COLLECTION_ZONE_PAY_DAILY_SUM);
        	isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing ZonePayDailySumDao.createZonePayDailySum error occurred.", e);
        }

        return isFine;
    }

    public ZonePayDailySumEntity getEntityWithZone(String zoneCode, Date dt) {
        ZonePayDailySumEntity entity = null;

        try {
            Query q = new Query(Criteria.where("zoneCode").is(zoneCode));
            q.addCriteria(Criteria.where("createDate").is(dt));
            entity = mongoTemplate.findOne(q, ZonePayDailySumEntity.class, Constants._COLLECTION_ZONE_PAY_DAILY_SUM);
        } catch (Exception e) {
            Logging.logError("Doing ZonePayDailySumDao.getEntityWithZone error occurred.", e);
        }

        return entity;
    }

}
