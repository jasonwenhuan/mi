package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.entity.AccountEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;

public class AccountDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public AccountEntity getAccountUuid4Two(String key, String appid) {
        AccountEntity account = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            account = mongoTemplate.findOne(q, AccountEntity.class, Constants._COLLECTION_ACCOUNT2_UUID);
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.getAccountUuid4Two error occurred.", e);
        }

        return account;
    }

    public void updateAccountUuid4Two(AccountEntity entity) {
        try {
            if (entity != null) {
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("devices", entity.getDevices().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_ACCOUNT2_UUID);
            }
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.updateAccountUuid4Two error occurred.", e);
        }
    }

    public AccountEntity getAccountUuid4One(String key, String appid) {
        AccountEntity account = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appid));
            account = mongoTemplate.findOne(q, AccountEntity.class, Constants._COLLECTION_ACCOUNT1_UUID);
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.getAccountUuid4One error occurred.", e);
        }
        return account;
    }

    public void updateAccountUuid4One(AccountEntity entity) {
        try {
            if (entity != null) {
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("devices", entity.getDevices().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_ACCOUNT1_UUID);
            }
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.updateAccountUuid4One error occurred.", e);
        }
    }

	public AccountEntity getAccount4Two(String key, String appId) {
        AccountEntity account = null;

        try {
            Query q = new Query(Criteria.where("key").is(key));
            q.addCriteria(Criteria.where("appid").is(appId));
            account = mongoTemplate.findOne(q, AccountEntity.class, Constants._COLLECTION_ACCOUNTS222);
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.getAccount4Two error occurred.", e);
        }

        return account;
	}

	public void updateAccount4Two(AccountEntity entity) {
        try {
            if (entity != null) {
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("devices", entity.getDevices().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
                mongoTemplate.upsert(q, update, Constants._COLLECTION_ACCOUNTS222);
            }
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.updateAccount4Two error occurred.", e);
        }
	}

	public AccountEntity getAccount4One(String key, String appId) {
		AccountEntity account = null;
		try {
			Query q = new Query(Criteria.where("key").is(key));
			q.addCriteria(Criteria.where("appid").is(appId));
			account = mongoTemplate.findOne(q, AccountEntity.class, Constants._COLLECTION_ACCOUNTS111);
		} catch (Exception e) {
			Logging.logError("Doing AccountDao.getAccount4One error occurred.", e);
		}
		return account;
	}

	public void updateAccount4One(AccountEntity entity) {
        try {
            if (entity != null) {
                Update update = new Update();
                update.set("phones", entity.getPhones().split(","));
                update.set("devices", entity.getDevices().split(","));
                Query q = new Query(Criteria.where("key").is(entity.getKey()));
                q.addCriteria(Criteria.where("appid").is(entity.getAppid()));
				mongoTemplate.upsert(q, update, Constants._COLLECTION_ACCOUNTS111);
            }
        } catch (Exception e) {
            Logging.logError("Doing AccountDao.updateAccount4One error occurred.", e);
        }
	}
}
