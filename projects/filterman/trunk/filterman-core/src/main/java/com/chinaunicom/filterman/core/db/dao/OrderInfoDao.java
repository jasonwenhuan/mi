package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.OrderInfoEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * User: larry
 */

public class OrderInfoDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveOrderInfo(String orderId, String uuid) {
        boolean isFine = true;

        try {
            OrderInfoEntity entity = new OrderInfoEntity();
            entity.setOrderId(orderId);
            entity.setUuid(uuid);
            entity.setCreateDate(new Date());

            mongoTemplate.save(entity, Constants._COLLECTION_ORDER_HISTORY);
        } catch (Exception e) {
            Logging.logError("Doing OrderInfoDao.saveOrderInfo error occurred.", e);
            isFine = false;
        }

        return isFine;
    }

    public String getUuid(String orderId) {
        String uuid = null;

        try {
            Query q = new Query(Criteria.where("_id").is(orderId));

            OrderInfoEntity entity = mongoTemplate.findOne(q, OrderInfoEntity.class, Constants._COLLECTION_ORDER_HISTORY);
            if (entity != null) {
                uuid = entity.getUuid();
            }
        } catch (Exception e) {
            Logging.logError("Doing OrderInfoDao.getUuid error occurred.", e);
        }

        return uuid;
    }

}
