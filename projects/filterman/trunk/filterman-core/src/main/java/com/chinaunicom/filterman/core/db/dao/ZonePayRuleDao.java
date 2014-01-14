package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import com.chinaunicom.filterman.core.db.entity.ZonePayRuleEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.regex.Pattern;

/**
 * User: larry
 */

public class ZonePayRuleDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public int getLimitRateWithLevel(int level) {
        int limitRate = 0;

        try {
            Query q = new Query(Criteria.where("level").is(level));
            ZonePayRuleEntity entity = mongoTemplate.findOne(q, ZonePayRuleEntity.class, Constants._COLLECTION_ZONE_PAY_RULE);
            if (entity != null) {
                limitRate = entity.getRateLimit();
            }

        } catch (Exception e) {
            Logging.logError("Doing ZonePayRuleDao.getLimitRateWithLevel error occurred.", e);
        }

        return limitRate;
    }

    public int getLimitRateWithZone(String zoneCode) {
        int limitRate = 0;

        try {
            Pattern pattern = Pattern.compile(".*" + zoneCode + ".*", Pattern.CASE_INSENSITIVE);
            Query q = new Query(Criteria.where("zoneCode").regex(pattern));
            ZonePayRuleEntity entity = mongoTemplate.findOne(q, ZonePayRuleEntity.class, Constants._COLLECTION_ZONE_PAY_RULE);
            if (entity != null) {
                limitRate = entity.getRateLimit();
            }
        } catch (Exception e) {
            Logging.logError("Doing ZonePayRuleDao.getLimitRateWithZone error occurred.", e);
        }

        return limitRate;
    }


}
