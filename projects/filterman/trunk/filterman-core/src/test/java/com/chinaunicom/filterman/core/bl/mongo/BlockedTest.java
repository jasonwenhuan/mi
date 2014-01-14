package com.chinaunicom.filterman.core.bl.mongo;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * User: Frank
 * Date: 13-12-12
 * Time: 上午10:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/chinaunicom/filterman/core/bl/mongo/test-context.xml")
public class BlockedTest {

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private Blocked b;

    @Test
    public void testExecute() throws Exception {

        Query q = new Query(Criteria.where("phone").is("15618003000"));
        mg.remove(q, Collections.BLOCKED);
        q = new Query(Criteria.where("phone").is("15618003001"));
        mg.remove(q, Collections.BLOCKED);

        BlockedEntity entity = new BlockedEntity();
        entity.setPhone("15618003001");
        entity.setCreated(new Date());
        entity.setMessage("test");
        mg.insert(entity, Collections.BLOCKED);

        RequestEntity request = new RequestEntity();
        request.setPhone("15618003000");
        b.execute(request);

        try {
            request.setPhone("15618003001");
            b.execute(request);
            assertTrue("疑似用户触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
