package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;

/**
 * User: larry
 */
public interface IRequestBL {

    public void saveRequestedMessage(RequestEntity entity);
    //public void saveCheckedMessage();
    public List<RequestEntity> getRequestsByOrderId(String orderId, int pageOffset, int rowsPerPage);
    public List<RequestEntity> getRequestsByPhoneNumber(String number, int pageOffset, int rowsPerPage);
    public List<RequestEntity> getAllRequests(int pageOffset, int rowsPerPage);
    public Long getRequestsCountByOrderId(String orderId, int pageOffset, int rowsPerPage);
    public Long getRequestsCountByPhoneNumber(String number, int pageOffset, int rowsPerPage);
    public Long getAllRequestsCount(int pageOffset, int rowsPerPage);
}
