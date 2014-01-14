package com.chinaunicom.filterman.core.db.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;
import com.chinaunicom.filterman.utilities.Logging;

public class RelatedPhoneRuleDao {
	@Autowired
    private MongoTemplate mongoTemplate;

	public boolean saveRelatedPhoneRule(RelatedPhoneRuleEntity entity) {
		boolean isFine = false;
    	try {
			mongoTemplate.save(entity, Constants._COLLECTION_RELATED_PHONE_RULE);
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneRuleDao.saveRelatedPhoneRule error occurred.", e);
        }
        return isFine;
	}

	public List<RelatedPhoneRuleEntity> getAllRelatedPhoneRules() {
		List<RelatedPhoneRuleEntity> entities = null;

        try {
            Query q = new Query();
            Sort sort = new Sort(Sort.Direction.ASC, "interval");
            q.with(sort);
            entities = mongoTemplate.find(q, RelatedPhoneRuleEntity.class, Constants._COLLECTION_RELATED_PHONE_RULE);
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneRuleDao.getAllRelatedPhoneRules error occurred.", e);
        }
        
        return entities;
	}

	public List<RelatedPhoneRuleEntity> getRulesByInterval(String interval) {
		List<RelatedPhoneRuleEntity> entities = null;

        try {
            Query q = new Query();
            Sort sort = new Sort(Sort.Direction.ASC, "interval");
            q.with(sort);
            /*Pattern pattern = Pattern.compile(".*" + interval + ".*", Pattern.CASE_INSENSITIVE);*/
            q.addCriteria(Criteria.where("interval").is(Integer.parseInt(interval)));
            entities = mongoTemplate.find(q, RelatedPhoneRuleEntity.class, Constants._COLLECTION_RELATED_PHONE_RULE);
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneRuleDao.getRulesByInterval error occurred.", e);
        }
        
        return entities;
	}

	public boolean updateRelatedPhoneRule(String key, int interval, int limit) {
		boolean isFine = false;
        try {
            Query q = new Query();
        	ObjectId id = new ObjectId(key);
        	q.addCriteria(Criteria.where("_id").is(id));
            Update update = new Update();
            update.set("interval", interval);
            update.set("frequentLimit", limit);
            mongoTemplate.updateFirst(q, update, Constants._COLLECTION_RELATED_PHONE_RULE);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneRuleDao.updateRelatedPhoneRule error occurred.", e);
        }
        return isFine;
	}

	public boolean deleteRule(String id) {
		boolean isFine = false;
        try {
            Query q = new Query();
        	ObjectId _id = new ObjectId(id);
        	q.addCriteria(Criteria.where("_id").is(_id));
            mongoTemplate.remove(q, Constants._COLLECTION_RELATED_PHONE_RULE);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneRuleDao.deleteRule error occurred.", e);
        }
        return isFine;
	}
}
