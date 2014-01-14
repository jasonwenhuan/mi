package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

/**
 * User: larry
 */

public class HardInfoEntity {
    private String _id;
    private String mac;
    private String imei;
    private String imsi;
    private Date updateDate;

    public String getUuid() {
        return _id;
    }

    public void setUuid(String id) {
        this._id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
