package com.chinaunicom.filterman.ui.controller;

import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * User: larry
 */
@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    WebServiceClient webServiceClient;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public ModelAndView test() {
        return new ModelAndView("hello", "users", webServiceClient.getUser());
    }
}
