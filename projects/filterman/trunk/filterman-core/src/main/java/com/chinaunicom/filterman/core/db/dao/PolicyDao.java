package com.chinaunicom.filterman.core.db.dao;

import java.util.Date;
import java.util.List;

import com.chinaunicom.filterman.constants.PolicyStatus;
import com.chinaunicom.filterman.core.db.entity.PolicyEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.utilities.Logging;

public class PolicyDao {
    @Autowired
    private MongoTemplate mongoTemplate;

	public void savePolicy(PolicyEntity entity) {
    	try {
			mongoTemplate.save(entity, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.savePolicy error occurred.", e);
        }
	}
	
	public List<PolicyEntity> getPolicies(String groupId, String groupLevel) {
		List<PolicyEntity> entities = null;

		try {
    		Query q = new Query();
    		q.addCriteria(Criteria.where("groupId").is(groupId));
    		if(!groupLevel.equals("-1")){
    			q.addCriteria(Criteria.where("level").is(groupLevel));
    		}else{
    			q.addCriteria(Criteria.where("status").is("0"));
    		}
            entities = mongoTemplate.find(q, PolicyEntity.class, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.getPolicies error occurred.", e);
        }

        return entities;
	}
	
	public PolicyEntity getPolicy(String id) {
		PolicyEntity entity = null;

		try {
    		Query q = new Query();
    		ObjectId groupPolicyId = new ObjectId(id);
    		q.addCriteria(Criteria.where("_id").is(groupPolicyId));
    		entity = mongoTemplate.findOne(q, PolicyEntity.class, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.getPolicy error occurred.", e);
        }

        return entity;
	}

    public PolicyEntity getAvailablePolicy(String groupId) {
        PolicyEntity entity = null;

        try {
            Query q = new Query();
            q.addCriteria(Criteria.where("groupId").is(groupId));
            q.addCriteria(Criteria.where("status").is(PolicyStatus.POLICY_VALID_STATUS));
            entity = mongoTemplate.findOne(q, PolicyEntity.class, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.getPolicy error occurred.", e);
        }

        return entity;
    }

    public void resetPolicyStatus(String groupId) {
        try {
            Update update = new Update();
            update.set("status", PolicyStatus.POLICY_INVALID_STATUS);
            Query q = new Query(Criteria.where("groupId").is(groupId));
            mongoTemplate.updateMulti(q, update, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.resetPolicyStatus error occurred.", e);
        }
    }

    public void updatePolicy(PolicyEntity entity) {
        try {
            if (entity != null) {
                Update update = new Update();
                update.set("policyIds", entity.getPolicyIds().split(","));
                update.set("level", entity.getLevel());
                update.set("status", PolicyStatus.POLICY_VALID_STATUS);
                update.set("updateDate", new Date());
                ObjectId _id = new ObjectId(entity.getId());
                Query q = new Query(Criteria.where("_id").is(_id));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_POLICIES);
            }
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.updatePolicy error occurred.", e);
        }
    }
    
    public void removePoliciesByGroupId(String groupId) {
        try {
			Query q = new Query(Criteria.where("groupId").is(groupId));
			mongoTemplate.remove(q, Constants._COLLECTION_POLICIES);
        } catch (Exception e) {
            Logging.logError("Doing PolicyDao.resetPolicyStatus error occurred.", e);
        }
    }
}
