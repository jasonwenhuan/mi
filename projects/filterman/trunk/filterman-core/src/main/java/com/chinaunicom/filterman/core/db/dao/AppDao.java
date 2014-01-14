package com.chinaunicom.filterman.core.db.dao;

import java.util.List;
import java.util.regex.Pattern;

import com.chinaunicom.filterman.core.db.entity.AppEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.data.mongodb.core.query.Update;

public class AppDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<AppEntity> getAllApps(int pageOffset, int rowsPerPage) {
        List<AppEntity> entities = null;

        try {
            Query q = new Query();
            if(pageOffset >=0 && rowsPerPage > 0){
       		 	q.skip(pageOffset).limit(rowsPerPage);
       	 	}
            entities = mongoTemplate.find(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getAllApps error occurred.", e);
        }

        return entities;
    }

    public List<AppEntity> getAppsByGroupId(String groupId, int pageOffset, int rowsPerPage) {
        List<AppEntity> entities = null;

        try {
            Query q = new Query();
            if(!groupId.equalsIgnoreCase("-1")){
                q.addCriteria(Criteria.where("groupId").is(groupId));
            }
            if(pageOffset >=0 && rowsPerPage > 0){
       		 	q.skip(pageOffset).limit(rowsPerPage);
       	 	}
            entities = mongoTemplate.find(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getAppsByGroupId error occurred.", e);
        }

        return entities;
    }
    
    public Long getAppsCountByGroupId(String groupId) {
    	Long count = null;
		try {
			Query q = new Query();
            if(!groupId.equalsIgnoreCase("-1")){
                q.addCriteria(Criteria.where("groupId").is(groupId));
            }
        	 count = mongoTemplate.count(q, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.getAppsCountByGroupId error occurred.", e);
        }
        return count;
	}

    public List<AppEntity> getAppsByAppName(String appName, int pageOffset,
			int rowsPerPage) {
        List<AppEntity> entities = null;
        try {
            Query q = new Query();
            if(!appName.equalsIgnoreCase("-1")){
                Pattern pattern = Pattern.compile(".*" + appName + ".*", Pattern.CASE_INSENSITIVE);
                q.addCriteria(Criteria.where("appName").regex(pattern));
            }
            if(pageOffset >=0 && rowsPerPage > 0){
    		 	q.skip(pageOffset).limit(rowsPerPage);
            }
            entities = mongoTemplate.find(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getAppsByName error occurred.", e);
        }

        return entities;
    }
    
    public Long getAppsCountByAppName(String appName) {
    	Long count = null;
		try {
        	 Query q = new Query();
             if(!appName.equalsIgnoreCase("-1")){
                 Pattern pattern = Pattern.compile(".*" + appName + ".*", Pattern.CASE_INSENSITIVE);
                 q.addCriteria(Criteria.where("appName").regex(pattern));
             }
        	 count = mongoTemplate.count(q, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.getAppsCountByAppName error occurred.", e);
        }
        return count;
	}

    public AppEntity getAppById(String id) {
		AppEntity entity = null;

        try {
            Query q = new Query();
            ObjectId _id = new ObjectId(id);

            q.addCriteria(Criteria.where("_id").is(_id));
            entity = mongoTemplate.findOne(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.getAppById error occurred.", e);
        }

        return entity;
	}

    public AppEntity getAppByIdAndName(String appId, String appName) {
        AppEntity entity = null;
        try {
            Query q = new Query(Criteria.where("appId").is(appId));
            q.addCriteria(Criteria.where("appName").is(appName));
            entity = mongoTemplate.findOne(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.getAppByIdAndName error occurred.", e);
        }

        return entity;
    }

    public void updateApp(AppEntity entity){
        try {
            Update update = new Update();
            update.set("groupId", entity.getGroupId());
            update.set("groupName", entity.getGroupName());
            update.set("updateDate", entity.getUpdateDate());
            update.set("updateUser", entity.getUpdateUser());
            ObjectId id = new ObjectId(entity.getId());
            Query q = new Query(Criteria.where("_id").is(id));
            mongoTemplate.upsert(q, update, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.updateApp error occurred.", e);
        }
    }

	public void saveApp(AppEntity entity){
		try {
			mongoTemplate.save(entity, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing GroupDao.saveApp error occurred.", e);
        }
	}

	public List<AppEntity> getAppsByAppId(String appId) {
		List<AppEntity> entities = null;
        try {
            Query q = new Query(Criteria.where("appId").is(appId));
            entities = mongoTemplate.find(q, AppEntity.class, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.getAppsByAppId error occurred.", e);
        }
        return entities;
	}

	public void resetAppGroup(String groupId) {
        try {
        	Update update = new Update();
            update.set("groupId", "-1");
            update.set("groupName", "-1");
            Query q = new Query(Criteria.where("groupId").is(groupId));
            mongoTemplate.updateMulti(q, update, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.resetAppGroup error occurred.", e);
        }
	}

	public Long getAppsCount() {
		Long count = null;
		try {
        	 Query q = new Query();
        	 count = mongoTemplate.count(q, Constants._COLLECTION_APPS);
        } catch (Exception e) {
            Logging.logError("Doing AppDao.getAppsCount error occurred.", e);
        }
        return count;
	}
}
