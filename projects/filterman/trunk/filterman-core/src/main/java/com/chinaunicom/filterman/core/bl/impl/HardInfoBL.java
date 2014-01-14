package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.comm.vo.HardInfoVO;
import com.chinaunicom.filterman.core.bl.IHardInfoBL;
import com.chinaunicom.filterman.core.db.dao.HardInfoDao;
import com.chinaunicom.filterman.core.db.dao.OrderInfoDao;
import com.chinaunicom.filterman.core.db.entity.HardInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: larry
 */

public class HardInfoBL implements IHardInfoBL {
    @Autowired
    private HardInfoDao hardInfoDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

    public String checkUuid(HardInfoVO hardInfoVO) {
        String uuid = null;

        HardInfoEntity entity = hardInfoDao.getHardInfoWithMii(
                hardInfoVO.getMac(),
                hardInfoVO.getImei(),
                hardInfoVO.getImsi());

        if (entity == null) {
            entity = hardInfoDao.getHardInfoWithMi(hardInfoVO.getMac(), hardInfoVO.getImei());
        }

        if (entity == null) {
            entity = hardInfoDao.getHardInfoWithIi(hardInfoVO.getImei(), hardInfoVO.getImsi());
        }

        if (entity == null) {
            hardInfoDao.saveHardInfo(hardInfoVO.getMac(),
                    hardInfoVO.getImei(),
                    hardInfoVO.getImsi());

            entity = hardInfoDao.getHardInfoWithMii(hardInfoVO.getMac(),
                    hardInfoVO.getImei(),
                    hardInfoVO.getImsi());
        }

        if (entity != null) {
            uuid = entity.getUuid();
            orderInfoDao.saveOrderInfo(hardInfoVO.getOrderId(), uuid);
        }

        return uuid;
    }
}
