package com.chinaunicom.filterman.core.bl.mongo;

import java.util.Map;

/**
 * User: Frank
 * Date: 13-12-12
 * Time: 下午3:31
 */
public class PayStatisticEntity {

    private long total;
    private Map<String, Long> provinces;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Map<String, Long> getProvinces() {
        return provinces;
    }

    public void setProvinces(Map<String, Long> provinces) {
        this.provinces = provinces;
    }
}
