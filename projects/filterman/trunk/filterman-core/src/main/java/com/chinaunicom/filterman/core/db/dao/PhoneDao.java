package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.entity.PhoneEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;

public class PhoneDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public PhoneEntity getPhoneUuid4Two(String key, String appid) {
        PhoneEntity phone = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            phone = mongoTemplate.findOne(q, PhoneEntity.class,
                    Constants._COLLECTION_PHONE2_UUID);
        } catch (Exception e) {
            Logging.logError("Doing PhoneDao.getPhoneUuid4Two error occurred.", e);
        }

        return phone;
    }

    public void updatePhoneUuid4Two(PhoneEntity entity) {
        try {
            if (entity != null){
                Update update = new Update();
                update.set("devices", entity.getDevices().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_PHONE2_UUID);
            }
        } catch (Exception e) {
            Logging.logError("Doing PhoneDao.updatePhoneUuid4Two error occurred.", e);
        }
    }

    public PhoneEntity getPhoneUuid4One(String key, String appid) {
        PhoneEntity phone = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            phone = mongoTemplate.findOne(q, PhoneEntity.class,
                    Constants._COLLECTION_PHONE1_UUID);
        } catch (Exception e) {
            Logging.logError("Doing PhoneDao.getPhoneUuid4One error occurred.", e);
        }

        return phone;

    }

    public void updatePhoneUuid4One(PhoneEntity entity) {
        if (entity != null){
            try {
                Update update = new Update();
                update.set("devices", entity.getDevices().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_PHONE1_UUID);
            } catch (Exception e) {
                Logging.logError("Doing PhoneDao.updatePhoneUuid4One error occurred.", e);
            }
        }
    }

	public PhoneEntity getPhone4Two(String key, String appId) {
		PhoneEntity phone = null;
        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appId));
            phone = mongoTemplate.findOne(q, PhoneEntity.class,
                    Constants._COLLECTION_PHONES222);
        } catch (Exception e) {
            Logging.logError("Doing PhoneDao.getPhone4Two error occurred.", e);
        }

		return phone;
	}

	public void updatePhone4Two(PhoneEntity entity) {
        try {
            if (entity != null){
                Update update = new Update();
                update.set("devices", entity.getDevices().split(","));
                update.set("accounts", entity.getAccounts().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_PHONES222);
            }
        } catch (Exception e) {
            Logging.logError("Doing PhoneDao.updatePhone4Two error occurred.", e);
        }
	}
	
	public PhoneEntity getPhone4One(String key, String appId) {
		PhoneEntity phone = null;
		try {
			Query q = new Query(Criteria.where("key").is(key));
			q.addCriteria(Criteria.where("appid").is(appId));
			phone = mongoTemplate.findOne(q, PhoneEntity.class,
					Constants._COLLECTION_PHONES111);
		} catch (Exception e) {
			Logging.logError("Doing PhoneDao.getPhone4One error occurred.", e);
		}
		return phone;
		
	}

	public void updatePhone4One(PhoneEntity entity) {
		if (entity != null){
			try {
				Update update = new Update();
				update.set("devices", entity.getDevices().split(","));
				update.set("accounts", entity.getAccounts().split(","));
				Query q = new Query(Criteria.where("key").is(entity.getKey()));
				q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
				mongoTemplate.upsert(q, update, Constants._COLLECTION_PHONES111);
			} catch (Exception e) {
				Logging.logError("Doing PhoneDao.updatePhone4One error occurred.", e);
			}
		}
	}
}
