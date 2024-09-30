package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    UserRole findByName(String name);

    ArrayList<UserRole> findByOrgId(long orgId);


//    @Query(value = "SELECT * FROM USERROLE userRole WHERE userRole.name != :ROLE_CUSTOMER", nativeQuery = true)
    List<UserRole> findByNameNotIn(ArrayList<String> name);

}
