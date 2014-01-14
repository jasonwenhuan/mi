package com.chinaunicom.filterman.core.bl.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * Date: 13-12-12
 * Time: 下午3:43
 */
public class PayRateEntity {

    private Date start;
    private Date end;
    private List<Province> provinces = new ArrayList<Province>();

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public static class Province {

        private String name;
        private long sum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }
    }
}
