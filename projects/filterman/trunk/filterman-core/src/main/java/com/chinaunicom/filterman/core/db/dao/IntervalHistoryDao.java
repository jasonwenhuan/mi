package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.IntervalHistoryEntity;
import com.chinaunicom.filterman.core.db.entity.MREntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * User: larry
 */

public class IntervalHistoryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveIntervalEntity(IntervalHistoryEntity entity) {
        boolean isFine = false;

        try {
            mongoTemplate.save(entity, Constants._COLLECTION_INTERVAL_HISTORY);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.saveIntervalEntity error occurred.", e);
        }

        return isFine;
    }

    public int getBillTotal(String phone, Date startTime, Date endTime) {

        int total = 0;

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("phone").is(phone));
            query.addCriteria(Criteria.where("createDate").gt(startTime).lte(endTime));

            MapReduceResults<MREntity> results = mongoTemplate.mapReduce(
                    query,
                    Constants._COLLECTION_INTERVAL_HISTORY,
                    mapFunction(),
                    reduceFunction(),
                    MREntity.class);
            if (results != null && results.iterator().hasNext()) {
                total = results.iterator().next().getValue().getBill();
            }

        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.getBillTotal error occurred.", e);
        }

        return total;
    }
    
    public boolean removeRecordsAndEnsureIndex() {
    	boolean isFine = false;
		try {
			Query query = new Query();
			mongoTemplate.remove(query, Constants._COLLECTION_INTERVAL_HISTORY);
			
			IndexOperations io=mongoTemplate.indexOps(Constants._COLLECTION_INTERVAL_HISTORY);
			io.dropAllIndexes();
			
			isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing IntervalMstDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
		return isFine;
	}

    private static String mapFunction() {
        StringBuffer rtn = new StringBuffer();
        rtn.append("function() {");
        rtn.append("emit(this.phone, {bill: this.bill});");
        rtn.append("}");
        return rtn.toString();
    }

    private static String reduceFunction() {
        StringBuffer rtn = new StringBuffer();
        rtn.append("function(key, values) {");
        rtn.append("var sum = 0;");
        rtn.append("values.forEach(function(doc){");
        rtn.append("sum += doc.bill;");
        rtn.append("}");
        rtn.append(");");
        rtn.append("return {bill: sum};");
        rtn.append("}");
        return rtn.toString();
    }

}
