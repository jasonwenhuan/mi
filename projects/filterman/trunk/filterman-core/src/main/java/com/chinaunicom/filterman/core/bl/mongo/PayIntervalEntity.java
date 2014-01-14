package com.chinaunicom.filterman.core.bl.mongo;

import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * Date: 13-12-10
 * Time: 下午2:14
 */
public class PayIntervalEntity {

    private String phone;
    private List<Pay> pays;
    private Date triggerDate = new Date(0);

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Pay> getPays() {
        return pays;
    }

    public void setPays(List<Pay> pays) {
        this.pays = pays;
    }

    public Date getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(Date triggerDate) {
        this.triggerDate = triggerDate;
    }

    public static class Pay {

        private Long fee;
        private Date created;

        public Pay() {
        }

        public Pay(Long fee, Date created) {
            this.fee = fee;
            this.created = created;
        }

        public Long getFee() {
            return fee;
        }

        public void setFee(Long fee) {
            this.fee = fee;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }
    }
}
