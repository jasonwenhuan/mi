package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;

/**
 *  by wenhuan
 */
public interface IDefenseBL {
    public boolean execute(RequestEntity request) throws PolicyException, RequestException;
}
