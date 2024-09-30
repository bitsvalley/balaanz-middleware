package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.Branch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    List<Branch> findByNameAndOrgId(String accountNumber, long orgID);

    List<Branch> findByOrgId( long orgID);

    Branch findByCodeAndOrgId(String code, long orgId);

}
