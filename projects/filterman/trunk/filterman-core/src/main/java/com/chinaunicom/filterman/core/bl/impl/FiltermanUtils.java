package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.db.entity.BlockedEntity;

import java.util.Date;

/**
 * User: larry
 */

public class FiltermanUtils {

    public static String getPrePhone(String phone) {
        String prePhone = "";

        if(phone.length() > 11){
            prePhone = phone.substring(2, 9);
        }else{
            prePhone = phone.substring(0, 7);
        }

        return prePhone;
    }

    public static int getRateLevel(float dailySumRate) {
        int level = 0;

        if (dailySumRate < 5) {
            level = 1;
        } else if (dailySumRate >= 5 && dailySumRate < 10) {
            level = 5;
        } else if (dailySumRate >= 10) {
            level = 10;
        }

        return level;
    }

    public static Date getStartDate(Date requestDate, int hours) {
        Date date = new Date(requestDate.getTime() - hours * 60 * 60 * 1000);

        return date;
    }

    public static BlockedEntity createBlockEntity(
            Date date,
            String phone,
            String message
    ) {
        BlockedEntity entity = new BlockedEntity();
        entity.setPhone(phone);
        entity.setCreated(date);
        entity.setMessage(message);
        return entity;
    }}
