package com.chinaunicom.filterman.core.bl.mongo;

import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * Date: 13-12-9
 * Time: 下午1:42
 */
public class SerialNumbersEntity {

    private String prefix;
    private List<Phone> phones;
    private Date triggerDate = new Date(0);

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Date getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(Date triggerDate) {
        this.triggerDate = triggerDate;
    }

    public static class Phone {
        private String number;
        private Date datetime;

        public Phone() {
        }

        public Phone(String number, Date datetime) {
            this.number = number;
            this.datetime = datetime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public Date getDatetime() {
            return datetime;
        }

        public void setDatetime(Date time) {
            this.datetime = time;
        }
    }
}
