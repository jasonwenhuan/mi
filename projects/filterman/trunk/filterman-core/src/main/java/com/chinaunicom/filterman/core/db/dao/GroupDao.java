package com.chinaunicom.filterman.core.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.utilities.Logging;

public class GroupDao {
    @Autowired
    private MongoTemplate mongoTemplate;
	
	public List<GroupEntity> getGroups() {
		List<GroupEntity> entities = new ArrayList<GroupEntity>();
        try {
            Query q = new Query();
            entities = mongoTemplate.find(q, GroupEntity.class, Constants._COLLECTION_GROUPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getGroups error occurred.", e);
        }
        return entities;
	}

    public List<GroupEntity> getGroupsByType(String type) {
        List<GroupEntity> entity = null;

        try {
            Query q = new Query(Criteria.where("type").is(type));
            entity = mongoTemplate.find(q, GroupEntity.class, Constants._COLLECTION_GROUPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getGroupsByType error occurred.", e);
        }

        return entity;
    }

    public GroupEntity getGroupById(String id) {
        GroupEntity entity = null;

        try {
            ObjectId _id = new ObjectId(id);
            Query q = new Query(Criteria.where("_id").is(id));
            entity = mongoTemplate.findOne(q, GroupEntity.class, Constants._COLLECTION_GROUPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getGroupById error occurred.", e);
        }

        return entity;
    }

    public void saveGroup(GroupEntity entity){
		try {
			mongoTemplate.save(entity, Constants._COLLECTION_GROUPS);
		} catch (Exception e) {
			Logging.logError("Doing GroupDao.saveGroup error occurred.", e);
		}
	}
	
	public void removeGroup(String dKey){
		try {
			ObjectId id = new ObjectId(dKey);
			Query q = new Query(Criteria.where("_id").is(id));
			mongoTemplate.remove(q, Constants._COLLECTION_GROUPS);
		} catch (Exception e) {
			Logging.logError("Doing GroupDao.removeGroup error occurred.", e);
		}
	}
}
