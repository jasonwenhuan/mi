package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IRequestBL;
import com.chinaunicom.filterman.core.db.dao.RequestDao;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;

/**
 *  by wenhuan
 */
public class RequestBL implements IRequestBL {
	@Autowired
    private RequestDao requestDao;

	public void saveRequestedMessage(RequestEntity entity) {
		requestDao.saveRequest(entity);
	}

	@Override
	public List<RequestEntity> getRequestsByOrderId(String orderId, int pageOffset, int rowsPerPage) {
		return requestDao.getRequestsByOrderId(orderId, pageOffset, rowsPerPage);
	}

	@Override
	public List<RequestEntity> getRequestsByPhoneNumber(String phoneNumber, int pageOffset, int rowsPerPage) {
		return requestDao.getRequestsByPhoneNumber(phoneNumber, pageOffset, rowsPerPage);
	}

	@Override
	public List<RequestEntity> getAllRequests(int pageOffset, int rowsPerPage) {
		return requestDao.getAllRequests(pageOffset, rowsPerPage);
	}
	
	@Override
	public Long getRequestsCountByOrderId(String orderId, int pageOffset, int rowsPerPage) {
		return requestDao.getRequestsCountByOrderId(orderId, pageOffset, rowsPerPage);
	}
	
	@Override
	public Long getRequestsCountByPhoneNumber(String phoneNumber, int pageOffset, int rowsPerPage) {
		return requestDao.getRequestsCountByPhoneNumber(phoneNumber, pageOffset, rowsPerPage);
	}
	
	@Override
	public Long getAllRequestsCount(int pageOffset, int rowsPerPage) {
		return requestDao.getAllRequestsCount(pageOffset, rowsPerPage);
	}
}
