package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.bl.mongo.Collections;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * User: Frank
 * Date: 13-12-12
 * Time: 下午3:20
 */
@Service
public class PayRate implements IDefenseBL {

    private final static String POLICY_NAME = "省市比率";
    private final static long HOUR = 60 * 60 * 1000;
    private final static long DAY = 24 * HOUR;

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private Collections c;

    @Override
    public boolean execute(RequestEntity request) throws PolicyException, RequestException {

        String phone = request.getPhone();
        String payfee = request.getPayfee();


        return true;
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
