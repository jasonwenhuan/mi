package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * Date: 13-12-10
 * Time: 下午2:05
 */
@Service
public class PayInterval implements IDefenseBL {

    private final static String POLICY_NAME = "支付间隔";
    private final static long HOUR = 60 * 60 * 1000;
    private final static long DAY = 24 * HOUR;

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private Collections c;

    @PostConstruct
    public void init() {

        Config cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.PAYINTERVAL)), Config.class, Collections.POLICYCFG);
        if(cfg == null) {
            cfg = new Config();
            cfg.setRange(DAY);
            cfg.addPhase(new Config.Phase(HOUR, 1000));
            cfg.addPhase(new Config.Phase(2 * HOUR, 1500));
            cfg.addPhase(new Config.Phase(6 * HOUR, 2000));
            cfg.addPhase(new Config.Phase(12 * HOUR, 3000));
            cfg.addPhase(new Config.Phase(24 * HOUR, 5000));

            mg.insert(cfg, Collections.POLICYCFG);
        }

    }

    @Override
    public boolean execute(RequestEntity request) throws PolicyException, RequestException {

        String appid = request.getAppid();
        String phone = request.getPhone();
        Long payfee = Long.parseLong(request.getPayfee());
        Date time = (request.getTimestamp() == null ? new Date() : request.getTimestamp());

        Config cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.PAYINTERVAL + appid)), Config.class, Collections.POLICYCFG);
        if(cfg == null) {
            cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.PAYINTERVAL)), Config.class, Collections.POLICYCFG);
        }

        Query q = new Query(Criteria.where("phone").is(phone));
        PayIntervalEntity entity = mg.findOne(q, PayIntervalEntity.class, Collections.PAYINTERVAL);

        if(entity != null) {
            pushPay(q, payfee, time);

            List<PayIntervalEntity.Pay> pays = entity.getPays();
            long number = payfee;
            for(int i = pays.size() - 1; i >= 0; i--) {
                PayIntervalEntity.Pay rd = pays.get(i);

                long dif = time.getTime() - rd.getCreated().getTime();
                if(dif > cfg.getRange() * HOUR) {
                    break;
                }

                number += rd.getFee();
                for(Config.Phase phase : cfg.getPhases()) {
                    if((dif <= phase.getTime() && number >= phase.getSum())) {
                        String msg = "触发[" + POLICY_NAME + "]策略, " + (phase.getTime() / HOUR) + "小时计费超过限额" + (phase.getSum() / 100) + "元，达到" + (number / 100) + "元";
                        setBlocked(phone, time, msg);
                        cleanPhone(phone);
                        throw new PolicyException(msg);
                    }
                }
            }

        } else {
            insertPhone(phone, payfee, time);
        }

        return true;
    }

    private void pushPay(Query q, Long payfee, Date now) {

        Update u = new Update();
        u.push("pays", new PayIntervalEntity.Pay(payfee, now));
        mg.updateFirst(q, u, Collections.PAYINTERVAL);

    }

    private void insertPhone(String phone, Long payfee, Date now) {

        PayIntervalEntity entity = new PayIntervalEntity();
        entity.setPhone(phone);
        entity.setPays(Arrays.asList(new PayIntervalEntity.Pay(payfee, now)));

        mg.insert(entity, Collections.PAYINTERVAL);

    }

    private void cleanPhone(String phone) {

        Query q = new Query(Criteria.where("phone").is(phone));
        mg.remove(q, Collections.PAYINTERVAL);

    }

    private void setBlocked(String phone, Date now, String msg) {

        BlockedEntity entity = new BlockedEntity(phone, now, msg);
        mg.insert(entity, Collections.BLOCKED);
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }

    private static class Config {
        private String name = Collections.PAYINTERVAL;
        private long range;
        private List<Phase> phases = new ArrayList<Phase>();

        private long getRange() {
            return range;
        }

        private void setRange(long range) {
            this.range = range;
        }

        private List<Phase> getPhases() {
            return phases;
        }

        private void setPhases(List<Phase> phases) {
            this.phases = phases;
        }

        private void addPhase(Phase phase) {
            this.phases.add(phase);
        }

        private long getPhaseRange() {
            return (phases.size() > 0) ? (phases.get(phases.size() - 1).getTime()) : 0;
        }

        private static class Phase {
            private long time;
            private long sum;

            private Phase() {
            }

            private Phase(long time, long sum) {
                this.time = time;
                this.sum = sum;
            }

            private long getTime() {
                return time;
            }

            private void setTime(long time) {
                this.time = time;
            }

            private long getSum() {
                return sum;
            }

            private void setSum(long sum) {
                this.sum = sum;
            }
        }
    }
}
