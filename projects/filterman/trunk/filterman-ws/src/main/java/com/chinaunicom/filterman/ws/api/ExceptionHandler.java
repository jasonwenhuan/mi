package com.chinaunicom.filterman.ws.api;

import com.chinaunicom.filterman.utilities.Logging;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Frank
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        Logging.logError(ex.getMessage());

        response.setStatus(200);
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write("<?xml version='1.0' encoding='UTF-8'?><paypass>1</paypass>");
            writer.flush();
        } catch (IOException e) {
            Logging.logError(e.getMessage());
        }

        return null;
    }
}
