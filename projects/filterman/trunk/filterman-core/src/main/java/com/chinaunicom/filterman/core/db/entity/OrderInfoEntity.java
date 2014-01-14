package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

/**
 * User: larry
 */

public class OrderInfoEntity {
    private String _id;
    private String uuid;
    private Date createDate;

    public String getOrderId() {
        return _id;
    }

    public void setOrderId(String id) {
        this._id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
