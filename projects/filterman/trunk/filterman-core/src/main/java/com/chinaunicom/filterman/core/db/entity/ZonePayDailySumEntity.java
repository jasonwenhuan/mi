package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

/**
 * User: larry
 */

public class ZonePayDailySumEntity {
    private Date createDate;
    private String zoneCode;
    private int dailySum;
    private int zoneSum;
    private float zoneRate;

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public int getZoneSum() {
        return zoneSum;
    }

    public void setZoneSum(int zoneSum) {
        this.zoneSum = zoneSum;
    }

    public float getZoneRate() {
        return zoneRate;
    }

    public void setZoneRate(float zoneRate) {
        this.zoneRate = zoneRate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getDailySum() {
        return dailySum;
    }

    public void setDailySum(int dailySum) {
        this.dailySum = dailySum;
    }

}
