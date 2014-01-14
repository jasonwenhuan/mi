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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Frank
 * Date: 13-12-10
 * Time: 下午2:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/chinaunicom/filterman/core/bl/mongo/test-context.xml")
public class PayIntervalTest {

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private PayInterval pi;

    @Test
    public void testExecute() throws Exception {

        clean("15618000000");

        RequestEntity entity = new RequestEntity();
        entity.setPhone("15618000000");
        entity.setPayfee("500");
        pi.execute(entity);

        entity = new RequestEntity();
        entity.setPhone("15618000000");
        entity.setPayfee("200");
        pi.execute(entity);

        try {
            entity = new RequestEntity();
            entity.setPhone("15618000000");
            entity.setPayfee("500");
            pi.execute(entity);
            assertTrue("支付间隔触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test1() throws Exception {

        clean("15618000000");

        PayIntervalEntity entity = new PayIntervalEntity();
        entity.setPhone("15618000000");

        List<PayIntervalEntity.Pay> pays = new ArrayList<PayIntervalEntity.Pay>();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -1);
        c.add(Calendar.MINUTE, -30);
        PayIntervalEntity.Pay pay = new PayIntervalEntity.Pay(800L, c.getTime());
        pays.add(pay);

        c.add(Calendar.MINUTE, 10);
        pay = new PayIntervalEntity.Pay(200L, c.getTime());
        pays.add(pay);

        c.add(Calendar.MINUTE, 5);
        pay = new PayIntervalEntity.Pay(300L, c.getTime());
        pays.add(pay);

        c.add(Calendar.MINUTE, 30);
        pay = new PayIntervalEntity.Pay(500L, c.getTime());
        pays.add(pay);

        entity.setPays(pays);

        mg.insert(entity, Collections.PAYINTERVAL);

        try {
            RequestEntity request = new RequestEntity();
            request.setPhone("15618000000");
            request.setPayfee("200");
            pi.execute(request);
            assertTrue("支付间隔触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void clean(String phone) {

        Query q = new Query(Criteria.where("phone").is(phone));
        mg.remove(q, Collections.PAYINTERVAL);
        mg.remove(q, Collections.BLOCKED);

    }
}
