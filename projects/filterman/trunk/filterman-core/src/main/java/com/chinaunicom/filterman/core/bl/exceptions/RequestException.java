package com.chinaunicom.filterman.core.bl.exceptions;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;

/**
 * User: Frank
 */
public class RequestException extends Exception {

    private RequestEntity entity;

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, RequestEntity entity) {
        super(message);
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + (entity == null ? "" : entity);
    }

    public RequestEntity getEntity() {
        return entity;
    }

    public void setEntity(RequestEntity entity) {
        this.entity = entity;
    }
}
