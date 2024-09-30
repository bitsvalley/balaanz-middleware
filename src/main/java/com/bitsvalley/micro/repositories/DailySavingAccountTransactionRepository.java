package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.DailySavingAccountTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DailySavingAccountTransactionRepository extends CrudRepository<DailySavingAccountTransaction, Long> {

    List<DailySavingAccountTransaction> findByDailySavingAccount(String savingAccount);

    Optional<DailySavingAccountTransaction> findByReferenceAndOrgId(String reference, long orgId);

    @Query(value = "SELECT * FROM daily_saving_account_transaction ca WHERE ca.created_date BETWEEN :startDate AND :endDate AND ca.org_id = :orgId", nativeQuery = true)
    List<DailySavingAccountTransaction> searchStartEndDate(String startDate, String endDate, long orgId);

    @Query(value = "SELECT * FROM daily_saving_account_transaction ca WHERE ca.created_date BETWEEN :startDate AND :endDate AND ca.created_by = :userName AND ca.org_id = :orgId", nativeQuery = true)
    List<DailySavingAccountTransaction> searchStartEndDate(String startDate, String endDate, String userName, long orgId);

    List<DailySavingAccountTransaction> findByDailySavingAccountId(long id, Pageable pageable);

    String AGENT_DAY_STATS = "SELECT withdrawal_deposit, SUM(saving_amount) " +
      "from daily_saving_account_transaction \n" +
      "where created_by = (SELECT u.user_name from user u WHERE id = :userId) AND created_date BETWEEN  :fromDate AND :toDate\n" +
      "GROUP BY withdrawal_deposit";
    @Query(value = AGENT_DAY_STATS, nativeQuery = true)
    List<Object[]> getAgentDailyStats(@Param("userId") String userId,
                                      @Param("fromDate") String fromDate,
                                      @Param("toDate") String toDate);

    String AGENT_HISTORY = "SELECT * " +
      "from daily_saving_account_transaction \n" +
      "where created_by = (SELECT u.user_name from user u WHERE id = :userId) ORDER BY created_date DESC limit 5";
    @Query(value = AGENT_HISTORY, nativeQuery = true)
    List<DailySavingAccountTransaction> findLatestTransactions(@Param("userId") long id);

    String AGENT_SELECTIVE_HISTORY = "SELECT * from daily_saving_account_transaction\n" +
      "where created_by = (SELECT u.user_name from user u WHERE id = :userId)\n" +
      "AND transaction_type = :type\n" +
      "ORDER BY created_date DESC";
    @Query(value = AGENT_SELECTIVE_HISTORY, nativeQuery = true)
    List<DailySavingAccountTransaction> findAgentsSelectiveTransactions(@Param("userId") long id,
                                                                        @Param("type") String transactionType);

    String AGENT_UNSIGNED_AMOUNT = "SELECT COALESCE(SUM(dsat.saving_amount), 0) from daily_saving_account_transaction dsat\n" +
      "WHERE dsat.created_by = :userName AND dsat.transaction_type = :type";
    @Query(value = AGENT_UNSIGNED_AMOUNT, nativeQuery = true)
    Double findAgentsUnsignedAmount(@Param("userName") String username,
                                     @Param("type") String transactionType);
    String REMIT_TRANSACTION = "UPDATE\n" +
      "\tdaily_saving_account_transaction d\n" +
      "SET\n" +
      "\tsigned_by = :signedBy,\n" +
      "\tsigned_date = :signedDate,\n" +
      "\ttransaction_type = :transactionType,\n" +
      "\tsigned = :signed\n" +
      "WHERE\n" +
      "\tid = :id";
    @Query(value = REMIT_TRANSACTION, nativeQuery = true)
    @Modifying
    void remitTransaction(@Param("signedBy") String signedBy,
                                                   @Param("signedDate") String signedDate,
                                                   @Param("transactionType") String transactionType,
                                                   @Param("signed") boolean isSigned,
                                                   @Param("id") long transactionId);
}
