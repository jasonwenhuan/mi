package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

/**
 * User: larry
 */

public class IntervalHistoryEntity {
    private String phone;
    private Date createDate;
    private int bill;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
}
