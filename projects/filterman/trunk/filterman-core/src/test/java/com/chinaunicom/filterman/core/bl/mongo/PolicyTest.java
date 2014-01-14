package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * User: Frank
 * Date: 13-12-23
 * Time: 上午11:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/chinaunicom/filterman/core/bl/mongo/test-context.xml")
public class PolicyTest {

    @Autowired
    private MongoTemplate mg;

    private static long count = 800000;



    @Test
    public void checkResult() throws Exception {

        long size = count / 10000;

        long hint = 0, miss = 0, error = 0, normal = 0;
        for(int i = 0; i < size; i++) {

            long start = System.currentTimeMillis();
            Query q = new Query().skip(i * 10000).limit(10000).with(new Sort(Sort.Direction.ASC, "TASKTIMEEND"));
            List<Log> logs = mg.find(q, Log.class, "logs");
            System.out.println("Get data[" + (i * 10000 + 1) + "-" + (i * 10000 + logs.size()) + "] use:" + (System.currentTimeMillis() - start) + "ms.");

            for(Log log : logs) {
                long get = mg.count(new Query(Criteria.where("phone").is(String.valueOf(log.getUSERCODE()))), "blockphone");
                long baddebt = mg.count(new Query(Criteria.where("USERCODE").is(log.getUSERCODE())), "baddebt");

                if(get > 0 && baddebt > 0) {
                    hint++;
                } else if(get > 0 && baddebt <= 0) {
                    error++;
                } else if(get <= 0 && baddebt > 0) {
                    miss++;
                } else {
                    normal++;
                }
            }
        }

        System.out.println("Hint is " + (hint * 100 / count) + "%\r\nMiss is " + (miss * 100 / count) + "%\r\nError is " + (error * 100 / count) + "%\r\nNormal is " + (normal * 100 / count) + "%");

    }

    public static class Result {
        private long _id;
        private long total;

        public long get_id() {
            return _id;
        }

        public void set_id(long _id) {
            this._id = _id;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }
    }

    public static class Request {
        private String phone;
        private String fee;
        private Date timestamp;
        private String msg;

        public Request(String phone, String fee, Date timestamp, String msg) {
            this.phone = phone;
            this.fee = fee;
            this.timestamp = timestamp;
            this.msg = msg;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class Log {
        private long USERCODE;
        private long FEE;
        private long TASKTIMEEND;

        public long getUSERCODE() {
            return USERCODE;
        }

        public void setUSERCODE(long USERCODE) {
            this.USERCODE = USERCODE;
        }

        public long getFEE() {
            return FEE;
        }

        public void setFEE(long FEE) {
            this.FEE = FEE;
        }

        public long getTASKTIMEEND() {
            return TASKTIMEEND;
        }

        public void setTASKTIMEEND(long TASKTIMEEND) {
            this.TASKTIMEEND = TASKTIMEEND;
        }
    }

}
