package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

/**
 * User: Sally
 */
public class BlackListDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public WBListEntity getBlackList(String keyId) {

        WBListEntity entity = null;

        try {
            Query query = new Query(Criteria.where("keyId").is(keyId));

            entity = mongoTemplate.findOne
                    (query, WBListEntity.class, Constants._COLLECTION_BLACKLIST);
        } catch (Exception e) {
            Logging.logError("Doing BlackListDao.getBlackList error occurred.", e);
        }

        return entity;
    }
    
    public List<WBListEntity> getBlackListByKeyId(String keyId, int pageOffset, int pageSize) {
    	List<WBListEntity> entities = null;
        try {
        	Query query = new Query();
        	if(!"-1".equals(keyId)){
        		Pattern pattern = Pattern.compile(".*" + keyId + ".*", Pattern.CASE_INSENSITIVE);
        		query.addCriteria(Criteria.where("keyId").regex(pattern));
        	}
        	if(pageOffset >=0 && pageSize > 0){
        		query.skip(pageOffset).limit(pageSize);
            }
        	entities = mongoTemplate.find
                    (query, WBListEntity.class, Constants._COLLECTION_BLACKLIST);
        } catch (Exception e) {
            Logging.logError("Doing BlackListDao.getBlackListByKeyId error occurred.", e);
        }

        return entities;
	}
    
    public Long getBlackListCountByKeyId(String keyId) {
    	Long count = null;
        try {
        	Query query = new Query();
        	if(!"-1".equals(keyId)){
        		Pattern pattern = Pattern.compile(".*" + keyId + ".*", Pattern.CASE_INSENSITIVE);
        		query.addCriteria(Criteria.where("keyId").regex(pattern));
        	}
        	count = mongoTemplate.count(query, Constants._COLLECTION_BLACKLIST);
        } catch (Exception e) {
            Logging.logError("Doing BlackListDao.getBlackListCountByKeyId error occurred.", e);
        }
        return count;
	}

    public void saveBlackList(WBListEntity entity) {
        try {
            mongoTemplate.save(entity, Constants._COLLECTION_BLACKLIST);
        } catch (Exception e) {
            Logging.logError("Doing BlackListDao.saveBlackList error occurred.", e);
        }
    }

    public void removeBlackList(String key) {
        try {
            ObjectId id = new ObjectId(key);
            Query query = new Query(Criteria.where("_id").is(id));
            mongoTemplate.remove(query, Constants._COLLECTION_BLACKLIST);
        } catch (Exception e) {
            Logging.logError("Doing BlackListDao.removeBlackList error occurred.", e);
        }
    }
}
