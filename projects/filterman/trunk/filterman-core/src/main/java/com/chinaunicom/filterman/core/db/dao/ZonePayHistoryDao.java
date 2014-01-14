package com.chinaunicom.filterman.core.db.dao;

import com.chinaunicom.filterman.core.db.Constants;
import com.chinaunicom.filterman.core.db.entity.MREntity;
import com.chinaunicom.filterman.core.db.entity.ZonePayHistoryEntity;
import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * User: larry
 */

public class ZonePayHistoryDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean saveEntity(ZonePayHistoryEntity entity) {
        boolean isFine = false;

        try {
            mongoTemplate.save(entity, Constants._COLLECTION_ZONE_PAY_HISTORY);
            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing ZonePayHistoryDao.saveEntity error occurred.", e);
        }

        return isFine;
    }

    public int getTotalBill(String zoneCode, Date startTime, Date endTime) {
        int bill = 0;

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("createDate").gt(startTime).lte(endTime));

            MapReduceResults<MREntity> results = mongoTemplate.mapReduce(
                    query,
                    Constants._COLLECTION_ZONE_PAY_HISTORY,
                    mapFunction(zoneCode),
                    reduceFunction(),
                    MREntity.class);
            if (results != null && results.iterator().hasNext()) {
                bill = results.iterator().next().getValue().getBill();
            }
        } catch (Exception e) {
            Logging.logError("Doing ZonePayHistoryDao.getTotalBill error occurred.", e);
        }

        return bill;
    }

    public int getTotalBillForZone(String zoneCode, Date startTime, Date endTime) {
        int bill = 0;

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("zoneCode").is(zoneCode));
            query.addCriteria(Criteria.where("createDate").gt(startTime).lte(endTime));

            MapReduceResults<MREntity> results = mongoTemplate.mapReduce(
                    query,
                    Constants._COLLECTION_ZONE_PAY_HISTORY,
                    mapFunction(zoneCode),
                    reduceFunction(),
                    MREntity.class);
            if (results != null && results.iterator().hasNext()) {
                bill = results.iterator().next().getValue().getBill();
            }
        } catch (Exception e) {
            Logging.logError("Doing ZonePayHistoryDao.getTotalBillForZone error occurred.", e);
        }

        return bill;
    }

    public boolean removeRecordsAndEnsureIndex() {
        boolean isFine = false;
        try {
            Query query = new Query();
            mongoTemplate.remove(query, Constants._COLLECTION_ZONE_PAY_HISTORY);

            IndexOperations io=mongoTemplate.indexOps(Constants._COLLECTION_ZONE_PAY_HISTORY);
            io.dropAllIndexes();

            isFine = true;
        } catch (Exception e) {
            Logging.logError("Doing ZonePayHistoryDao.removeRecordsAndEnsureIndex error occurred.", e);
        }
        return isFine;
    }

    private static String mapFunction(String zoneCode) {
        StringBuffer rtn = new StringBuffer();
        rtn.append("function() {");
        rtn.append("emit(");
        rtn.append(zoneCode);
        rtn.append(", {bill: this.bill});");
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
