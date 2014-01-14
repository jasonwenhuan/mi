package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: Frank
 * Date: 13-12-9
 * Time: 下午1:41
 */
@Service
public class SerialNumbers implements IDefenseBL {

    private final static String POLICY_NAME = "连号";
    private final static long HOUR = 60 * 60 * 1000;
    private final static long DAY = 24 * HOUR;

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private Collections c;

    @PostConstruct
    public void init() {

        Config cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.SERIAL_NUMBERS)), Config.class, Collections.POLICYCFG);
        if(cfg == null) {
            cfg = new Config();
            cfg.setRange(DAY);
            cfg.addPhase(new Config.Phase(HOUR, 2));
            cfg.addPhase(new Config.Phase(2 * HOUR, 4));
            cfg.addPhase(new Config.Phase(6 * HOUR, 6));
            cfg.addPhase(new Config.Phase(12 * HOUR, 8));
            cfg.addPhase(new Config.Phase(24 * HOUR, 10));

            mg.insert(cfg, Collections.POLICYCFG);
        }

    }

    @Override
    public boolean execute(RequestEntity request) throws PolicyException, RequestException {

        String appid = request.getAppid();
        String phone = request.getPhone();
        String prefix = phone.substring(0, 7);
        Date time = (request.getTimestamp() == null ? new Date() : request.getTimestamp());

        Config cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.SERIAL_NUMBERS + appid)), Config.class, Collections.POLICYCFG);
        if(cfg == null) {
            cfg = mg.findOne(new Query(Criteria.where("name").is(Collections.SERIAL_NUMBERS)), Config.class, Collections.POLICYCFG);
        }

        Query q = new Query(Criteria.where("prefix").is(prefix));
        SerialNumbersEntity entity = mg.findOne(q, SerialNumbersEntity.class, Collections.SERIAL_NUMBERS);

        if(entity != null) {

            if((time.getTime() - entity.getTriggerDate().getTime()) < cfg.getRange()) {
                String msg = "触发[" + POLICY_NAME + "]策略, " + "[触发在" + (cfg.getRange() / DAY) + "天之内]";
                setBlocked(phone, time, msg);
                throw new PolicyException(msg);
            }

            pushPhone(q, phone, time);

            List<SerialNumbersEntity.Phone> phones = entity.getPhones();
            long number = 0;
            for(int i = phones.size() - 1; i >= 0; i--) {
                SerialNumbersEntity.Phone rd = phones.get(i);
                if(rd.getNumber().equals(phone)) {
                    break;
                }

                long dif = time.getTime() - rd.getDatetime().getTime();
                if(dif > cfg.getPhaseRange()) {
                    break;
                }

                ++number;
                for(Config.Phase phase : cfg.getPhases()) {
                    if(dif <= phase.getTime() && number >= phase.getNumber()) {
                        List<String> numbers = new ArrayList<String>();
                        numbers.add(phone);
                        for(; i < phones.size(); i++) {
                            numbers.add(phones.get(i).getNumber());
                        }

                        String msg = "触发[" + POLICY_NAME + "]策略, " + (phase.getTime() / HOUR) + "小时超过" + phase.getNumber() + "个连号[" + StringUtils.collectionToCommaDelimitedString(numbers) + "]";
                        setBlocked(numbers, time, msg);
                        trigger(q, time);
                        throw new PolicyException(msg);
                    }
                }
            }

        } else {
            insertPhone(prefix, phone, time);
        }

        return true;

    }

    private void insertPhone(String prefix, String phone, Date now) {

        SerialNumbersEntity entity = new SerialNumbersEntity();
        entity.setPrefix(prefix);
        entity.setPhones(Arrays.asList(new SerialNumbersEntity.Phone(phone, now)));

        mg.insert(entity, Collections.SERIAL_NUMBERS);

    }

    private void pushPhone(Query q, String phone, Date now) {

        Update u = new Update();
        u.pull("phones", new BasicDBObject("number", phone));
        mg.updateFirst(q, u, Collections.SERIAL_NUMBERS);

        u = new Update();
        u.push("phones", new SerialNumbersEntity.Phone(phone, now));
        mg.updateFirst(q, u, Collections.SERIAL_NUMBERS);

    }

    private void trigger(Query q, Date now) {

        Update u = new Update();
        u.set("triggerDate", now);
        u.set("phones", new ArrayList<SerialNumbersEntity.Phone>());

        mg.updateFirst(q, u, Collections.SERIAL_NUMBERS);
    }

    private void setBlocked(String phone, Date now, String msg) {

        BlockedEntity entity = new BlockedEntity(phone, now, msg);
        mg.insert(entity, Collections.BLOCKED);
    }

    private void setBlocked(List<String> phones, Date now, String msg) {

        List<BlockedEntity> entities = new ArrayList<BlockedEntity>();
        for(String phone : phones) {
            entities.add(new BlockedEntity(phone, now, msg));
        }

        mg.insert(entities, Collections.BLOCKED);
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }

    private static class Config {
        private String name = Collections.SERIAL_NUMBERS;
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
            private long number;

            private Phase() {
            }

            private Phase(long time, long number) {
                this.time = time;
                this.number = number;
            }

            private long getTime() {
                return time;
            }

            private void setTime(long time) {
                this.time = time;
            }

            private long getNumber() {
                return number;
            }

            private void setNumber(long number) {
                this.number = number;
            }
        }

    }
}
