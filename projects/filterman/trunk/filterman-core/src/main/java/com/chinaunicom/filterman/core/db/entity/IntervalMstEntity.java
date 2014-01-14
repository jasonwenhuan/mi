package com.chinaunicom.filterman.core.db.entity;

/**
 * User: larry
 */

public class IntervalMstEntity {
    private String _id;
    private int interval;
    private int limitTotal;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getLimitTotal() {
        return limitTotal;
    }

    public void setLimitTotal(int limitTotal) {
        this.limitTotal = limitTotal;
    }
}
