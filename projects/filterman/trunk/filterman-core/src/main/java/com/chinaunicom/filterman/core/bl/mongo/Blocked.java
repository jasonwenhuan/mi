package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * User: Frank
 * Date: 13-12-12
 * Time: 上午9:00
 */
@Service
public class Blocked implements IDefenseBL {

    private final static String POLICY_NAME = "疑似用户";

    @Autowired
    private MongoTemplate mg;

    @Override
    public boolean execute(RequestEntity request) throws PolicyException, RequestException {

        String phone = request.getPhone();

        Query q = new Query(Criteria.where("phone").is(phone));
        BlockedEntity entity = mg.findOne(q, BlockedEntity.class, Collections.BLOCKED);

        if(entity != null) {
            throw new PolicyException("触发[" + POLICY_NAME + "]策略， " + entity.getMessage());
        }

        return true;
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
