package com.chinaunicom.filterman.core.db.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.MREntity;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneEntity;
import com.chinaunicom.filterman.utilities.Logging;

public class RelatedPhoneDao {
	@Autowired
    private MongoTemplate mongoTemplate;

	public boolean saveRelatedPhone(RelatedPhoneEntity entity) {
		boolean isFine = false;
    	try {
			mongoTemplate.save(entity, Constants._COLLECTION_RELATED_PHONE);
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneDao.saveRelatedPhone error occurred.", e);
        }
        return isFine;
	}
	
	public int getTotalFrequent(String phone, Date startTime, Date endTime) {
		int total = 0;
    	try {
    		Query query = new Query();
            query.addCriteria(Criteria.where("shortnumber").is(phone));
            query.addCriteria(Criteria.where("created").gt(startTime).lte(endTime));
            
            
            MapReduceResults<MREntity> results = mongoTemplate.mapReduce(
                    query,
                    Constants._COLLECTION_RELATED_PHONE,
                    mapFunction(),
                    reduceFunction(),
                    MREntity.class);
            if (results != null && results.iterator().hasNext()) {
                total = results.iterator().next().getValue().getBill();
            }
            
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneDao.getTotalFrequent error occurred.", e);
        }
        return total;
	}

	public List<RelatedPhoneEntity> getFullPhonesByShortPhone(String shortPhone) {
		List<RelatedPhoneEntity> relatedPhones= null;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("shortnumber").is(shortPhone));
			relatedPhones = mongoTemplate.find(query, RelatedPhoneEntity.class, Constants._COLLECTION_RELATED_PHONE);
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneDao.getFullPhonesByShortPhone error occurred.", e);
        }
		return relatedPhones;
	}
	
	public boolean removeRecordsAndEnsureIndex() {
		boolean isFine = false;
		try {
			Query query = new Query();
			mongoTemplate.remove(query, Constants._COLLECTION_RELATED_PHONE);
			
			IndexOperations io=mongoTemplate.indexOps(Constants._COLLECTION_RELATED_PHONE);
			io.dropAllIndexes();
			
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing RelatedPhoneDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
		return isFine;
	}
	
	 private static String mapFunction() {
	        StringBuffer rtn = new StringBuffer();
	        rtn.append("function() {");
	        rtn.append("emit(this.shortnumber, {bill: 1});");
	        rtn.append("}");
	        return rtn.toString();
	    }

	    private static String reduceFunction() {
	        StringBuffer rtn = new StringBuffer();
	        rtn.append("function(key, values) {");
	        rtn.append("var sum = 0;");
	        rtn.append("values.forEach(function(doc){");
	        rtn.append("sum++;");
	        rtn.append("}");
	        rtn.append(");");
	        rtn.append("return {bill: sum};");
	        rtn.append("}");
	        return rtn.toString();
	    }
}
