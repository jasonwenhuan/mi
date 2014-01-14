package com.chinaunicom.filterman.core.db.entity;

/**
 * User: larry
 */

public class ZonePayRuleEntity {
    private int level;
    private int rateLimit;
    private String zoneCode;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(int rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }
}
