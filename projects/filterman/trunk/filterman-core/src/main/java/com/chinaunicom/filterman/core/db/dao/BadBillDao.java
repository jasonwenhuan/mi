package com.chinaunicom.filterman.core.db.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.BadBillEntity;
import com.chinaunicom.filterman.utilities.Logging;

public class BadBillDao {
	@Autowired
    private MongoTemplate mongoTemplate;

    public List<BadBillEntity> getAllBadBills() {
    	List<BadBillEntity> entities = null;

        try {
            Query query = new Query();
            entities = mongoTemplate.find(query, BadBillEntity.class, Constants._COLLECTION_BAD_BILL);
        } catch (Exception e) {
            Logging.logError("Doing BadBillDao.getAllBadBills error occurred.", e);
        }

        return entities;
    }
    
    public boolean createBadBill(BadBillEntity entity){
    	boolean isFine = false;
    	try{
    		mongoTemplate.save(entity, Constants._COLLECTION_BAD_BILL);
    		isFine = true;
    	}catch(Exception e){
    		Logging.logError("Doing BadBillDao.createBadBill error occurred.", e);
    	}
    	return isFine;
    }

	public boolean removeRecordsAndEnsureIndex() {
		boolean isFine = false;
		try {
			Query query = new Query();
			mongoTemplate.remove(query, Constants._COLLECTION_BAD_BILL);
			
			IndexOperations io = mongoTemplate.indexOps(Constants._COLLECTION_BAD_BILL);
			io.dropAllIndexes();
			
			Index index =new Index();
	        index.on("userCode",Order.ASCENDING);
		    //index.unique();
		    io.ensureIndex(index);
		    
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BadBillDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
		return isFine;
	}

	public BadBillEntity getBadBillByUserCode(String userCode) {
		BadBillEntity entity = null;

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("userCode").is(userCode));
            entity = mongoTemplate.findOne(query, BadBillEntity.class, Constants._COLLECTION_BAD_BILL);
        } catch (Exception e) {
            Logging.logError("Doing BadBillDao.getBadBillByUserCode error occurred.", e);
        }

        return entity;
	}

	public boolean updateBadBillById(BadBillEntity entity) {
		boolean isFine = false;
		
		try {
            Update update = new Update();
            update.set("fee", entity.getFee());
            Query q = new Query();
            ObjectId _id = new ObjectId(entity.getId());
            q.addCriteria(Criteria.where("_id").is(_id));
            mongoTemplate.updateFirst(q, update, Constants._COLLECTION_BAD_BILL);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing BadBillDao.updateBadBillById error occurred.", e);
        }
        
        return isFine;
	}
}
