package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.comm.vo.UserVO;

import java.util.List;

/**
 * User: larry
 */
public interface IUserBL {

    public List<UserVO> getAllUsers();
    public UserVO getUser(String userName, String password);
}
