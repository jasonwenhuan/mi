package com.chinaunicom.filterman.core.bl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: Frank
 * Date: 13-12-11
 * Time: 上午9:09
 */
@Component
public class Collections {

    @Autowired
    private MongoTemplate mg;

    public static final String SERIAL_NUMBERS = "serialnumbers";
    public static final String PAYINTERVAL = "payinterval";
    public static final String BLOCKED = "blocked";
    public static final String POLICYCFG = "policycfg";

    private static final String[][] INITTABLE = {
            {SERIAL_NUMBERS, "prefix|_prefix_|unique"},
            {PAYINTERVAL, "phone|_phone_|unique"},
            {BLOCKED, "phone|_phone_|unique,created|_created_"},
            {POLICYCFG, "name|_name_|unique"}
    };

    @PostConstruct
    public void initCollections() {

        for(String[] collection : INITTABLE) {

            if(!mg.collectionExists(collection[0])) {
                mg.createCollection(collection[0]);

                IndexOperations idxOps = mg.indexOps(collection[0]);
                for(String idx : collection[1].split(",")) {
                    String[] opt = idx.split("\\|");
                    Index def = new Index(opt[0], Order.ASCENDING).named(opt[1]);
                    if(opt.length == 3) {
                        def.unique();
                    }
                    idxOps.ensureIndex(def);
                }
            }
        }
    }

}
