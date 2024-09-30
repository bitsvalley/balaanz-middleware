package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.LedgerAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LedgerAccountRepository extends CrudRepository<LedgerAccount, Long> {

    public LedgerAccount findByNameAndOrgIdAndActiveTrue(String name, long org);

    public LedgerAccount findByNameAndOrgId(String name, long org);

    @Query(value = "SELECT * FROM ledgeraccount la WHERE la.id != :id AND la.org_id = :orgId AND la.active = :active", nativeQuery = true)
    List<LedgerAccount>  findAllExceptActive(Long id, long orgId, boolean active);

    //    @Query(value = "SELECT * FROM ledgeraccount la WHERE la.org_id = :orgId, nativeQuery = true)
    List<LedgerAccount>  findByOrgIdAndActiveTrue( long orgId);
    List<LedgerAccount>  findByOrgIdAndActiveFalse(long orgId);

    List<LedgerAccount>  findByOrgId( long orgId);

    @Query(value = "SELECT * FROM ledgeraccount la where la.org_id = :orgId AND la.active =:active ORDER BY NAME ASC", nativeQuery = true)
    List<LedgerAccount>  findAllAlphabeticallyAndActive(long orgId);

    //    @Query(value = "SELECT * FROM LEDGERACCOUNT la WHERE la.code = :code" AND la.active =:active, nativeQuery = true)
    LedgerAccount  findByCodeAndOrgIdAndActiveTrue(String code, long orgId);
    //
    String LEDGER_ACCOUNTS = "select category, count(active),\n" +
      "      sum(active=0) as active_count,\n" +
      "      sum(active != 0) as inactive_count\n" +
      "      from balanz101db.ledgeraccount\n" +
      "      where org_id=:orgId\n" +
      "      group by category;";
    @Query(value = LEDGER_ACCOUNTS, nativeQuery = true)
    List<Object[]> findLedgerAccountsByCategory(@Param(value = "orgId") Integer orgId);

    @Query(value = "SELECT * FROM ledgeraccount la where la.org_id = :orgId AND la.code =:code", nativeQuery = true)
    List<LedgerAccount> findByCodeAndOrgId(String code, long orgId);
}
