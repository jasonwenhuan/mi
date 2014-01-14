package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

/**
 * User: larry
 */

public class CreditEntity {
    private String phone;
    private String imsi;
    private String level;
    private Date update;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
