package com.chinaunicom.filterman.core.bl.mongo;

import java.util.Date;

/**
 * User: Frank
 * Date: 13-12-10
 * Time: 上午11:27
 */
public class BlockedEntity {

    String phone;
    Date created;
    String message;

    public BlockedEntity() {
    }

    public BlockedEntity(String phone, Date created, String message) {
        this.phone = phone;
        this.created = created;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
