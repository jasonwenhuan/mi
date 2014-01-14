package com.chinaunicom.filterman.core.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * User: larry
 */

public class CreditDao {
    @Autowired
    private MongoTemplate mongoTemplate;

}
