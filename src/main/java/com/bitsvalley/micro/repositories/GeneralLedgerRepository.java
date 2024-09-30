package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.GeneralLedger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface GeneralLedgerRepository extends CrudRepository<GeneralLedger, Long> {

    @Query(value = "SELECT DISTINCT created_By FROM generallegder gl WHERE gl.org_id = :orgId ORDER BY gl.created_by DESC", nativeQuery = true)
    public ArrayList<String> findAllDistinctByCreatedBy(long orgId);

    public ArrayList<GeneralLedger> findByOrgId(long orgId);

    List<GeneralLedger> findByAccountNumberAndOrgId(String accountNumber, long orgId);

    List<GeneralLedger> findByReferenceAndOrgId(String reference, long orgId);

    List<GeneralLedger> findGLByTypeAndOrgId(String type, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> findAllOldestFirst(long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaStartEndDate(String startDate, String endDate, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.created_by = :createdBy AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithCreatedBy(String startDate, String endDate, String createdBy, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.org_id = :orgId AND gl.branch_code = :branchCode ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaStartEndDate(String startDate, String endDate, long orgId, String branchCode);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.created_by = :createdBy AND gl.org_id = :orgId AND gl.branch_code = :branchCode ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithCreatedBy(String startDate, String endDate, String createdBy, long orgId, String branchCode);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.account_number = :accountNumber AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithAccountNumber(String startDate, String endDate, String accountNumber, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.created_by = :createdBy AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithCreatedByAndLedgerAccount(String startDate, String endDate, String createdBy, long ledgerAccount, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.created_by = :createdBy AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId AND gl.branch_code = :branchCode ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithCreatedByAndLedgerAccount(String startDate, String endDate, String createdBy, long ledgerAccount, long orgId, String branchCode);


//    @Query(value = "SELECT * FROM GENERALLEGDER gl WHERE gl.type = :type AND gl.created_date BETWEEN :startDate AND :endDate AND account_number = :accountNumber", nativeQuery = true)
//    List<GeneralLedger> searchCriteriaWithAccountNumberAndType(String type, String startDate, String endDate, String accountNumber);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaLedger(String startDate, String endDate, long ledgerAccount, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId AND gl.branch_code = :branchCode ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaLedger(String startDate, String endDate, long ledgerAccount, long orgId, String branchCode);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.account_number = :accountNumber AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithAccountNumberLedger(String startDate, String endDate, String accountNumber, long ledgerAccount, long orgId);

    @Query(value = "SELECT * FROM generallegder gl WHERE gl.type = :type AND gl.created_date BETWEEN :startDate AND :endDate AND gl.account_number = :accountNumber AND gl.ledger_account_id = :ledgerAccount AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    List<GeneralLedger> searchCriteriaWithAccountNumberAndTypeLedger(String type, String startDate, String endDate, String accountNumber, long ledgerAccount, long orgId);

    @Query(value = "SELECT SUM(amount) FROM generallegder gl WHERE gl.created_date BETWEEN :startDate AND :endDate AND gl.ledger_account_id = :ledgerAccount AND gl.type = :debit AND gl.org_id = :orgId ORDER BY gl.CREATED_DATE ASC", nativeQuery = true)
    Double searchCriteriaLedgerType( String startDate, String endDate, long ledgerAccount, String debit, long orgId );

}
