package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.comm.vo.UserVO;
import com.chinaunicom.filterman.core.bl.IUserBL;
import com.chinaunicom.filterman.core.db.dao.UserDao;
import com.chinaunicom.filterman.core.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User: larry
 */
public class UserBL implements IUserBL {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserVO> getAllUsers() {
        List<UserVO> list = new ArrayList<UserVO>();

        List<UserEntity> entities = userDao.getAllUsers();
        if (entities != null && entities.size() > 0) {
            UserVO vo = null;
            for (UserEntity entity : entities) {
                vo = new UserVO();
                vo.setUsername(entity.getName());
                //ToDo: Do not output password!!!!!
                list.add(vo);
                // just output one record!!!!
                break;
            }
        }

        return list;
    }

    @Override
    public UserVO getUser(String userName, String password){
        UserVO user = new UserVO();
        UserEntity userEntity= userDao.getUser(userName, password);
        if(userEntity != null){
            user.setUsername(userEntity.getName());
            user.setPassword(userEntity.getPassword());
        }

        return user;
    }
}
