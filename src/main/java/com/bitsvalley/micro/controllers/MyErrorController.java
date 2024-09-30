package com.bitsvalley.micro.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * The ErrorController spits out details of an exception to the UI using model attributes
 *
 * @author  Fru Chifen
 * @version 1.0
 * @since   2021-06-10
 */
@Controller
public class MyErrorController implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        ModelAndView mv = new ModelAndView();

        mv.addObject("exception", ex.getLocalizedMessage());
        mv.addObject("url", request.getRequestURL());

        mv.setViewName("error");
        return mv;
    }

//    @GetMapping("/error")
//    @ExceptionHandler(Exception.class)
//        public String handleError(HttpServletRequest request, Exception ex) {
//        ex.printStackTrace();
//            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//            if (status != null) {
//                Integer statusCode = Integer.valueOf(status.toString());
//
//                if(statusCode == HttpStatus.NOT_FOUND.value()) {
////                    return "error-404";
//                    return "error";
//                }
//                else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
////                    return "error-500";
//                    return "error";
//                }
//            }
//            return "login";
//        }
}
