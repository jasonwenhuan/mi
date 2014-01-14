package com.chinaunicom.filterman.ws.api;

import com.chinaunicom.filterman.comm.vo.UserVO;
import com.chinaunicom.filterman.core.bl.IUserBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: larry
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserBL userBL;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllUsers() {
        return userBL.getAllUsers();
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Object getUser(@RequestBody UserVO userVO){
        return userBL.getUser(userVO.getUsername(), userVO.getPassword());
    }
}
