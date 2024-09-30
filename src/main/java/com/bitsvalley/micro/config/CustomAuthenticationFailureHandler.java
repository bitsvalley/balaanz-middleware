package com.bitsvalley.micro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * The CustomAuthenticationFailureHandler provides a failure handle after a unsuccessful login
 *
 * @author  Fru Chifen
 * @version 1.0
 * @since   2021-06-18
 */
public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
        String referer = request.getHeader("referer");
        if(referer == null){
            referer = "login";
        }else{
            referer =referer;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
            HashMap<String, Object> data = new HashMap<>();
            data.put(
                    "timestamp",
                    Calendar.getInstance().getTime());
            data.put(
                    "exception",
                    exception.getMessage());

            response.sendRedirect(referer);
            
    }
}
