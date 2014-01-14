package com.chinaunicom.filterman.core.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.GeneralBillEntity;
import com.chinaunicom.filterman.utilities.Logging;

public class GeneralBillDao {
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public boolean createGeneralBill(GeneralBillEntity entity){
		boolean isFine = false;
		try {
			mongoTemplate.save(entity, Constants._COLLECTION_GENERAL_BILL);
			isFine = true;
		} catch (Exception e) {
			Logging.logError("Doing GeneralBillDao.createGeneralBill error occurred.", e);
		}
		return isFine;
	}

	public boolean removeRecordsAndEnsureIndex() {
		boolean isFine = false;
		try {
			Query query = new Query();
			mongoTemplate.remove(query, Constants._COLLECTION_GENERAL_BILL);
			
			/*IndexOperations io = mongoTemplate.indexOps(Constants._COLLECTION_GENERAL_BILL);
			io.dropAllIndexes();
			
			Index index =new Index();
	        index.on("userCode",Order.ASCENDING);
		    index.unique();
		    io.ensureIndex(index);*/
		    
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing GeneralBillDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
		return isFine;
	}
	
	public List<GeneralBillEntity> getGeneralBillByOffsetAndCount(int offset,
			int count) {
		List<GeneralBillEntity> entities = null;
		try {
			Query query = new Query();
			query.skip(offset).limit(count);
			entities = mongoTemplate.find(query, GeneralBillEntity.class, Constants._COLLECTION_GENERAL_BILL);
		} catch (Exception e) {
			Logging.logError("Doing GeneralBillDao.getGeneralBillByOffsetAndCount error occurred.", e);
		}
		return entities;
	}
}
