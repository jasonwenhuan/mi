package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * User: larry
 */

public class IntervalMstDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveIntervalEntity(IntervalMstEntity entity) {
        boolean isFine = false;

        try {
            mongoTemplate.save(entity, Constants._COLLECTION_INTERVAL_MST);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.saveIntervalEntity error occurred.", e);
        }

        return isFine;
    }

    public boolean updateIntervalEntity(IntervalMstEntity entity) {
        boolean isFine = false;
        String id = entity.getId();
        int limitTotal = entity.getLimitTotal();
        try {
            Query q = new Query();
            ObjectId _id = new ObjectId(id);
            q.addCriteria(Criteria.where("_id").is(_id));
            Update update = new Update();
            update.set("limitTotal", limitTotal);
            mongoTemplate.updateFirst(q, update, Constants._COLLECTION_INTERVAL_MST);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.updateIntervalEntity error occurred.", e);
        }

        return isFine;
    }

    public List<IntervalMstEntity> getAllIntervals() {
        List<IntervalMstEntity> entities = null;

        try {
            Query q = new Query();
            Sort sort = new Sort(Sort.Direction.ASC, "interval");
            q.with(sort);
            entities = mongoTemplate.find(q, IntervalMstEntity.class, Constants._COLLECTION_INTERVAL_MST);
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.getAllIntervals error occurred.", e);
        }

        return entities;
    }

	public List<IntervalMstEntity> getRulesByInterval(String interval) {
		List<IntervalMstEntity> entities = null;

        try {
            Query q = new Query();
            Sort sort = new Sort(Sort.Direction.ASC, "interval");
            q.with(sort);
            /*Pattern pattern = Pattern.compile(".*" + interval + ".*", Pattern.CASE_INSENSITIVE);*/
            q.addCriteria(Criteria.where("interval").is(Integer.parseInt(interval)));
            entities = mongoTemplate.find(q, IntervalMstEntity.class, Constants._COLLECTION_INTERVAL_MST);
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.getRulesByInterval error occurred.", e);
        }
        
        return entities;
	}

	public boolean removeInterval(String id) {
		boolean isFine = false;
        try {
            Query q = new Query();
        	ObjectId _id = new ObjectId(id);
        	q.addCriteria(Criteria.where("_id").is(_id));
            mongoTemplate.remove(q, Constants._COLLECTION_INTERVAL_MST);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.removeInterval error occurred.", e);
        }
        return isFine;
	}
}
