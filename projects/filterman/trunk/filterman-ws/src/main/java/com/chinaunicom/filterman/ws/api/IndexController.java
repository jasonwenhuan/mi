package com.chinaunicom.filterman.ws.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: larry
 */

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method= RequestMethod.GET)
    public String index() {
        return "index";
    }
}
