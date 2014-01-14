package com.chinaunicom.filterman.core.db.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.BlockedEntity;
import com.chinaunicom.filterman.utilities.Logging;

public class BlockphoneDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveBlockPhoneEntity(BlockedEntity entity) {
        boolean isFine = false;

        try {
            mongoTemplate.save(entity, Constants._COLLECTION_BLOCK_PHONE);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BlockPhoneDao.saveBlockPhoneEntity error occurred.", e);
        }

        return isFine;
    }

    public boolean saveZonePayBlock(BlockedEntity entity) {
        boolean isFine = false;

        try {
            mongoTemplate.save(entity, Constants._COLLECTION_ZONE_PAY_BLOCK);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BlockPhoneDao.saveZonePayBlock error occurred.", e);
        }

        return isFine;
    }

    public BlockedEntity getBlockByPhone(String phone) {
        try {
            Query q = new Query();
            q.addCriteria(Criteria.where("phone").is(phone));
            BlockedEntity blockedEntity = mongoTemplate.findOne(q, BlockedEntity.class, Constants._COLLECTION_BLOCK_PHONE);
            return blockedEntity;
        } catch (Exception e) {
            Logging.logError("Doing BlockPhoneDao.getBlockByPhone error occurred.", e);
        }
        return null;
    }

    public boolean updateBlockByPhone(String phone, Date date) {
        boolean isFine = false;
        try {
            Query q = new Query();
            Update update = new Update();
            update.set("created", date);
            q.addCriteria(Criteria.where("phone").is(phone));
            mongoTemplate.updateFirst(q, update, Constants._COLLECTION_BLOCK_PHONE);
            return isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BlockPhoneDao.updateBlockByPhone error occurred.", e);
        }
        return isFine;
    }

    public boolean removeRecordsAndEnsureIndex() {
        boolean isFine = false;
        try {
            Query query = new Query();
            mongoTemplate.remove(query, Constants._COLLECTION_BLOCK_PHONE);
            mongoTemplate.remove(query, Constants._COLLECTION_ZONE_PAY_BLOCK);

            IndexOperations io=mongoTemplate.indexOps(Constants._COLLECTION_BLOCK_PHONE);
            io.dropAllIndexes();

            io=mongoTemplate.indexOps(Constants._COLLECTION_ZONE_PAY_BLOCK);
            io.dropAllIndexes();

            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BlockPhoneDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
        return isFine;
    }
}
