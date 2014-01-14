package com.chinaunicom.filterman.core.db.dao;

import java.util.List;
import java.util.regex.Pattern;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.utilities.Logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.chinaunicom.filterman.core.db.Constants;

public class RequestDao {
    @Autowired
    private MongoTemplate mongoTemplate;

	public void saveRequest(RequestEntity entity) {
        try {
		    mongoTemplate.save(entity, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.saveRequest error occurred.", e);
        }
	}

	public List<RequestEntity> getRequestsByOrderId(String orderId, int pageOffset, int rowsPerPage) {
		List<RequestEntity> requests = null;
        try {
        	 Pattern pattern = Pattern.compile(".*" + orderId + ".*", Pattern.CASE_INSENSITIVE);
        	 Query q = new Query(Criteria.where("orderid").regex(pattern));
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 if(pageOffset >=0 && rowsPerPage > 0){
        		 q.skip(pageOffset).limit(rowsPerPage);
        	 }
        	 requests = mongoTemplate.find(q, RequestEntity.class, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getRequestsByOrderId error occurred.", e);
        }
        return requests;
	}
	
	public Long getRequestsCountByOrderId(String orderId, int pageOffset, int rowsPerPage){
		Long count = null;
        try {
        	 Pattern pattern = Pattern.compile(".*" + orderId + ".*", Pattern.CASE_INSENSITIVE);
        	 Query q = new Query(Criteria.where("orderid").regex(pattern));
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 count = mongoTemplate.count(q, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getRequestsCountByOrderId error occurred.", e);
        }
        return count;
	}
	
	public List<RequestEntity> getRequestsByPhoneNumber(String phoneNumber, int pageOffset, int rowsPerPage) {
		List<RequestEntity> requests = null;
		try {
			 Pattern pattern = Pattern.compile(".*" + phoneNumber + ".*", Pattern.CASE_INSENSITIVE);
        	 Query q = new Query(Criteria.where("phone").regex(pattern));
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 if(pageOffset >=0 && rowsPerPage > 0){
        		 q.skip(pageOffset).limit(rowsPerPage);
        	 }
        	 requests = mongoTemplate.find(q, RequestEntity.class, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getRequestsByPhoneNumber error occurred.", e);
        }
        return requests;
	}
	
	public Long getRequestsCountByPhoneNumber(String phoneNumber, int pageOffset, int rowsPerPage){
		Long count = null;
		try {
			 Pattern pattern = Pattern.compile(".*" + phoneNumber + ".*", Pattern.CASE_INSENSITIVE);
        	 Query q = new Query(Criteria.where("phone").regex(pattern));
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 count = mongoTemplate.count(q, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getRequestsCountByPhoneNumber error occurred.", e);
        }
        return count;
	}
	
	public List<RequestEntity> getAllRequests(int pageOffset, int rowsPerPage) {
		List<RequestEntity> requests = null;
		try {
        	 Query q = new Query();
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 if(pageOffset >=0 && rowsPerPage > 0){
        		 q.skip(pageOffset).limit(rowsPerPage);
        	 }
        	 requests = mongoTemplate.find(q, RequestEntity.class, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getRequestsByPhoneNumber error occurred.", e);
        }
        return requests;
	}
	
	public Long getAllRequestsCount(int pageOffset, int rowsPerPage){
		Long count = null;
		try {
        	 Query q = new Query();
        	 q.addCriteria(Criteria.where("checkFlg").is(false));
        	 Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
             q.with(sort);
        	 count = mongoTemplate.count(q, Constants._COLLECTION_REQUEST);
        } catch (Exception e) {
            Logging.logError("Doing RequestDao.getAllRequestsCount error occurred.", e);
        }
        return count;
	}
}
