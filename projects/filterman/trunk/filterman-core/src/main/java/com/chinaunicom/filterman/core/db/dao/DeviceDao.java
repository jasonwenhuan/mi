package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.entity.DeviceEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;

public class DeviceDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public DeviceEntity getDeviceUuid4Two(String key, String appid) {
        DeviceEntity device = null;
        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            device = mongoTemplate.findOne(q, DeviceEntity.class, Constants._COLLECTION_DEVICE2_UUID);
        } catch (Exception e) {
            Logging.logError("Doing DeviceDao.getDeviceUuid4Two error occurred.", e);
        }

        return device;
    }

    public void updateDeviceUuid4Two(DeviceEntity entity) {
        try {
            if(entity != null){
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_DEVICE2_UUID);
            }
        } catch (Exception e) {
            Logging.logError("Doing DeviceDao.updateDeviceUuid4Two error occurred.", e);
        }
    }

    public DeviceEntity getDeviceUuid4One(String key, String appid) {
        DeviceEntity device = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            device = mongoTemplate.findOne(q, DeviceEntity.class,
                    Constants._COLLECTION_DEVICE1_UUID);
        } catch (Exception e) {
            Logging.logError("Doing DeviceDao.getDeviceUuid4One error occurred.", e);
        }

        return device;
    }

    public void updateDeviceUuid4One(DeviceEntity entity) {
        if(entity != null){
            try {
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_DEVICE1_UUID);
            } catch (Exception e) {
                Logging.logError("Doing DeviceDao.updateDeviceUuid4One error occurred.", e);
            }
        }
    }

	public DeviceEntity getDevice4Two(String key, String appId) {
		DeviceEntity device = null;
        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appId));
            device = mongoTemplate.findOne(q, DeviceEntity.class, Constants._COLLECTION_DEVICES222);
        } catch (Exception e) {
            Logging.logError("Doing DeviceDao.getDevice4Two error occurred.", e);
        }

		return device;
	}

	public void updateDevice4Two(DeviceEntity entity) {
        try {
            if(entity != null){
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_DEVICES222);
            }
        } catch (Exception e) {
            Logging.logError("Doing DeviceDao.updateDevice4Two error occurred.", e);
        }
	}
	
	public DeviceEntity getDevice4One(String key, String appId) {
		DeviceEntity device = null;
		try {
			Query q = new Query(Criteria.where("key").is(key));
			q.addCriteria(Criteria.where("appid").is(appId));
			device = mongoTemplate.findOne(q, DeviceEntity.class,
					Constants._COLLECTION_DEVICES111);
		} catch (Exception e) {
			Logging.logError("Doing DeviceDao.getDevice4One error occurred.", e);
		}
		return device;
	}

	public void updateDevice4One(DeviceEntity entity) {
		if(entity != null){
			try {
				Update update = new Update();
				update.set("phones", entity.getPhones().split(","));
				update.set("accounts", entity.getAccounts().split(","));
				Query q = new Query(Criteria.where("key").is(entity.getKey()));
				q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
				mongoTemplate.upsert(q, update, Constants._COLLECTION_DEVICES111);
			} catch (Exception e) {
				Logging.logError("Doing DeviceDao.updateDevice4One error occurred.", e);
			}
		}
	}
}
