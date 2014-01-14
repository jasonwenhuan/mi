package com.chinaunicom.filterman.ui.controller;

import com.chinaunicom.filterman.comm.vo.UserVO;
import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Sally
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    WebServiceClient webServiceClient;

    @RequestMapping(method = RequestMethod.GET)
    public String login4Default() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelMap login() {
        return new ModelMap();
    }

    @RequestMapping(value = "loginValidate", method = RequestMethod.POST)
    public ModelAndView validateUser(UserVO userVO) {
        UserVO vo = webServiceClient.getUserByKey(userVO);
        if (vo != null) {
            return new ModelAndView("redirect:groupManager");
        } else {
            boolean errorMsg = false;
            return new ModelAndView("login", "result", errorMsg);
        }
    }
}
