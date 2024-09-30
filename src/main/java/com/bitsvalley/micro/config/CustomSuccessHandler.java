package com.bitsvalley.micro.config;

import com.bitsvalley.micro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The CustomSuccessHandler provides a handle after a successful login
 *
 * @author  Fru Chifen
 * @version 1.0
 * @since   2021-06-20
 */
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response, final Authentication authentication)
        throws IOException, ServletException {

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();

//        if (customer.isOTPRequired()) {
//            customerService.clearOTP(customer);
//        }
//        User aUser = userRepository.findByUserName(username);
//        if(null == aUser){
//            callCenterService.saveCallCenterLog("", username, "", " @ "+ 0 +" - Logged in balaanz at "+ BVMicroUtils.formatDateTime(LocalDateTime.now()));
//            request.getSession().setAttribute(BVMicroUtils.CURRENT_ORG, 0);
//        }else{
//            callCenterService.saveCallCenterLog("", username, "", " @ "+ aUser.getOrgId()+" - Logged in balaanz at "+ BVMicroUtils.formatDateTime(LocalDateTime.now()));
//            request.getSession().setAttribute(BVMicroUtils.CURRENT_ORG, aUser.getOrgId());
//        }
        response.sendRedirect("/welcome");

    }

}
