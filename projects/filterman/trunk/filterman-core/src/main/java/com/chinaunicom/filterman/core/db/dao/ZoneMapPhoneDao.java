package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.ZoneMapPhoneEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

/**
 * User: larry
 */

public class ZoneMapPhoneDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public String getZoneCodeWithPhone(String phone) {
        String zoneCode = "other";

        try {
            Query q = new Query(Criteria.where("prePhone").is(phone));
            ZoneMapPhoneEntity entity = mongoTemplate.findOne(q, ZoneMapPhoneEntity.class, Constants._COLLECTION_ZONE_PHONE_MAP);
            if (entity != null) {
                zoneCode = entity.getZoneCode();
            }
        } catch (Exception e) {
            Logging.logError("Doing ZoneMapPhoneDao.getZoneCodeWithPhone error occurred.", e);
        }

        return zoneCode;
    }

    public boolean createZoneMapPhone(ZoneMapPhoneEntity entity) {
        boolean isFine = false;
        try {
            mongoTemplate.save(entity, Constants._COLLECTION_ZONE_PHONE_MAP);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing ZoneMapPhoneDao.createZoneMapPhone error occurred.", e);
        }
        return isFine;
    }

    public boolean removeRecordsAndEnsureIndex() {
        boolean isFine = false;
        try {
            Query query = new Query();
            mongoTemplate.remove(query, Constants._COLLECTION_ZONE_PHONE_MAP);

            IndexOperations io = mongoTemplate.indexOps(Constants._COLLECTION_ZONE_PHONE_MAP);
            io.dropAllIndexes();

            Index index =new Index();
            index.on("prePhone", Order.ASCENDING);
            //index.unique();
            io.ensureIndex(index);

            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing ZoneMapPhoneDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
        return isFine;
    }
    
}
