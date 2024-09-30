package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.repositories.UserRoleRepository;
import com.bitsvalley.micro.utils.BVMicroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class SuperService {

    @Autowired
    UserRoleRepository userRoleRepository;

    public String getLoggedInUserName() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();

    }

    public boolean isContainsRole(List<UserRole> userRoles, String aUserRole) {
        UserRole aRole = userRoleRepository.findByName(aUserRole);
        if (userRoles.contains(aRole))
            return true;
        return false;
    }
}
