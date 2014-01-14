package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.HardInfoEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * User: larry
 */

public class HardInfoDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveHardInfo(String mac, String imei, String imsi) {
        boolean isFine = true;

        try {
            HardInfoEntity entity = new HardInfoEntity();
            entity.setMac(mac);
            entity.setImei(imei);
            entity.setImsi(imsi);
            entity.setUpdateDate(new Date());

            mongoTemplate.save(entity, Constants._COLLECTION_HARD_INFO);
            Logging.logError("Doing HardInfoDao.saveHardInfo error occurred.");
        } catch (Exception e) {
            Logging.logError("Doing HardInfoDao.saveHardInfo error occurred.", e);
            isFine = false;
        }

        return isFine;
    }

    public HardInfoEntity getHardInfoWithMii(String mac, String imei, String imsi) {
        HardInfoEntity entity = null;

        try {
            Query q = new Query();
            q.addCriteria(Criteria.where("mac").is(mac));
            q.addCriteria(Criteria.where("imei").is(imei));
            q.addCriteria(Criteria.where("imsi").is(imsi));

            entity = mongoTemplate.findOne(q, HardInfoEntity.class, Constants._COLLECTION_HARD_INFO);
        } catch (Exception e) {
            Logging.logError("Doing HardInfoDao.getHardInfoWithMii error occurred.", e);
        }

        return entity;
    }

    public HardInfoEntity getHardInfoWithMi(String mac, String imei) {
        HardInfoEntity entity = null;

        try {
            Query q = new Query();
            q.addCriteria(Criteria.where("mac").is(mac));
            q.addCriteria(Criteria.where("imei").is(imei));

            entity = mongoTemplate.findOne(q, HardInfoEntity.class, Constants._COLLECTION_HARD_INFO);
        } catch (Exception e) {
            Logging.logError("Doing HardInfoDao.getHardInfoWithMi error occurred.", e);
        }

        return entity;
    }

    public HardInfoEntity getHardInfoWithIi(String imei, String imsi) {
        HardInfoEntity entity = null;

        try {
            Query q = new Query();
            q.addCriteria(Criteria.where("imei").is(imei));
            q.addCriteria(Criteria.where("imsi").is(imsi));

            entity = mongoTemplate.findOne(q, HardInfoEntity.class, Constants._COLLECTION_HARD_INFO);
        } catch (Exception e) {
            Logging.logError("Doing HardInfoDao.getHardInfoWithIi error occurred.", e);
        }

        return entity;
    }
}
