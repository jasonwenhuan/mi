package com.chinaunicom.filterman.ws.api;

import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.utilities.Logging;
import com.chinaunicom.filterman.ws.validate.RequestValidate;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 *  by wenhuan
 */

@Controller
@RequestMapping(value = "/pay")
public class PayController {
	
	@Autowired
    private RequestValidate requestValidate;

    private static final boolean silent = false;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void check(HttpServletRequest request, HttpServletResponse response) throws RequestException, IOException {

        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        String input = null;
        String requestBody = "";
        while((input = reader.readLine()) != null) {
            requestBody = requestBody + input;
        }

        Logging.logDebug(requestBody);

        String isPass =  requestValidate.validate(requestBody) ? "0" : "1";

        PrintWriter writer = response.getWriter();
        writer.write("<?xml version='1.0' encoding='UTF-8'?><paypass>" + (silent ? "0" : isPass) + "</paypass>");
        writer.flush();

    }
    
    @RequestMapping(value = "/check4test", method = RequestMethod.POST)
    public @ResponseBody String check(@RequestBody RequestEntity entity) throws RequestException, IOException {
        Logging.logDebug(entity.toString());
        String isPass =  requestValidate.validate(entity) ? "0" : "1";
        return isPass;
    }
}
