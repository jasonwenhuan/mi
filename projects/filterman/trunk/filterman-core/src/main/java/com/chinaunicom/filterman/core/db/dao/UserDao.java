package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.UserEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * User: larry
 */
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = null;

        try {
            Query q = new Query();

            users = mongoTemplate.find(q, UserEntity.class, Constants._COLLECTION_USERS_);
        } catch (Exception e) {
            Logging.logError("Doing UserDao.getAllUsers error occurred.", e);
        }

        return users;
    }

    public UserEntity getUser(String userName, String password){
        UserEntity user = null;
        try {
            Query q = new Query(Criteria.where("name").is(userName));
            q.addCriteria(Criteria.where("password").is(password));
            user = mongoTemplate.findOne(q, UserEntity.class,
                    Constants._COLLECTION_USERS_);
        } catch (Exception e) {
            Logging.logError("Doing UserDao.getUser error occurred.", e);
        }

        return user;
    }
}
