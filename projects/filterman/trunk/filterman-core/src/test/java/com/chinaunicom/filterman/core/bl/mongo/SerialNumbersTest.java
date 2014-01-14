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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Frank
 * Date: 13-12-9
 * Time: 下午1:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/chinaunicom/filterman/core/bl/mongo/test-context.xml")
public class SerialNumbersTest {

    @Autowired
    private MongoTemplate mg;

    @Autowired
    private SerialNumbers sn;

    @Test
    public void testExecute() throws Exception {

        clean("1561800", new String[] {"15618001000", "15618001001", "15618001002"});

        RequestEntity request = new RequestEntity();
        request.setTimestamp(new Date());
        request.setPhone("15618001000");
        sn.execute(request);

        request = new RequestEntity();
        request.setTimestamp(new Date());
        request.setPhone("15618001001");
        sn.execute(request);

        try {
            request = new RequestEntity();
            request.setPhone("15618001002");
            request.setTimestamp(new Date());
            sn.execute(request);
            assertTrue("连号触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test1() throws Exception {

        clean("1561800", new String[] {"15618001000", "15618001001", "15618001002", "15618001003", "15618002000", "15618002001", "15628001000"});

        SerialNumbersEntity entity = new SerialNumbersEntity();
        entity.setPrefix("1561800");

        List<SerialNumbersEntity.Phone> phones = new ArrayList<SerialNumbersEntity.Phone>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -1);
        c.add(Calendar.MINUTE, -30);
        SerialNumbersEntity.Phone phone = new SerialNumbersEntity.Phone("15618001000", c.getTime());  //1小时30分钟前
        phones.add(phone);

        c.add(Calendar.MINUTE, 10);
        phone = new SerialNumbersEntity.Phone("15618001001", c.getTime()); //1小时20分钟前
        phones.add(phone);

        c.add(Calendar.MINUTE, 5);
        phone = new SerialNumbersEntity.Phone("15618001002", c.getTime());  //1小时15分钟前
        phones.add(phone);

        c.add(Calendar.MINUTE, 30);
        phone = new SerialNumbersEntity.Phone("15618001003", c.getTime());  //45分钟前
        phones.add(phone);

        entity.setPhones(phones);

        mg.insert(entity, Collections.SERIAL_NUMBERS);

        try {
            RequestEntity request = new RequestEntity();
            request.setTimestamp(new Date());
            request.setPhone("15618002000");
            sn.execute(request);
            assertTrue("连号触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            RequestEntity request = new RequestEntity();
            request.setPhone("15618002001");
            sn.execute(request);
            assertTrue("连号触发应该被枪毙了呀！", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            RequestEntity request = new RequestEntity();
            request.setPhone("15628001000");
            sn.execute(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue("连号触发应该被枪毙了呀！", false);
        }

    }

    private void clean(String prefix, String[] phones) {

        Query q = new Query(Criteria.where("prefix").is(prefix));
        mg.remove(q, Collections.SERIAL_NUMBERS);

        for(String phone : phones) {
            q = new Query(Criteria.where("phone").is(phone));
            mg.remove(q, Collections.BLOCKED);
        }

    }
}
