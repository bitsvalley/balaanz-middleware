package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Optional<UserRole> findUserRoleById(long id) {
        return userRoleRepository.findById(id);
    }
    public UserRole findUserRoleByName(String name, long orgId) {
        return userRoleRepository.findByName(name);
    }

    public List<UserRole> findUserRoleByOrgId(long orgId) {
        List<UserRole> byOrgId = userRoleRepository.findByOrgId(orgId);
        return byOrgId;
    }

    public void saveUserRole(UserRole userRole, long orgId){
        userRole.setOrgId(orgId);
        userRoleRepository.save(userRole);
    }

    public void saveAllRole(Iterable<UserRole> iterableUserRole){
        Iterable<UserRole> iterable = userRoleRepository.saveAll(iterableUserRole);
    }

    public Iterable<UserRole>  findAll(){
        Iterable<UserRole> all = userRoleRepository.findAll();
        return all;
    }

}
