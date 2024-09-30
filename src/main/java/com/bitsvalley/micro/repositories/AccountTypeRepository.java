package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.AccountType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {

//    AccountType findByNameAndOrgId(String name, long orgId);
//    AccountType findByNumberAndOrgId(String number, long orgId);

//    List<AccountType> findByOrgId(long orgId);
//    List<AccountType> findByOrgIdAndCategory(long orgId, String category);

    AccountType findByNameAndOrgIdAndActiveTrue(String name, long orgId);
    AccountType findByNumberAndOrgIdAndActiveTrue(String number, long orgId);

    List<AccountType> findByOrgIdAndActiveTrue(long orgId);

    List<AccountType> findByOrgIdAndName(long orgId, String name);

    List<AccountType> findByOrgId(long orgId);

    List<AccountType> findByOrgIdAndCategoryAndActiveTrue(long orgId, String category);

    String ALL_ACCOUNTS_SUM = "SELECT \n" +
      "    u.id ,\n" +
      "    COALESCE(SUM(d.account_balance), 0) AS daily_account_balance,\n" +
      "    COALESCE(SUM(s.account_balance), 0) AS saving_account_balance,\n" +
      "    COALESCE(SUM(c.account_balance), 0) AS current_account_balance,\n" +
      "    COALESCE(SUM(sa.account_balance), 0) AS share_account_balance,\n" +
      "    COALESCE(SUM(l.loan_amount), 0) AS loan_amount,\n" +
      "    COALESCE(SUM(d.account_balance), 0) + \n" +
      "    COALESCE(SUM(s.account_balance), 0) + \n" +
      "    COALESCE(SUM(sa.account_balance), 0) +\n" +
      "    COALESCE(SUM(c.account_balance), 0) -\n" +
      "    COALESCE(SUM(l.loan_amount), 0) AS total\n" +
      "FROM \n" +
      "    user u\n" +
      "LEFT JOIN \n" +
      "    dailysavingaccount d ON u.id = d.user_id\n" +
      "LEFT JOIN \n" +
      "    savingaccount s ON u.id = s.user_id\n" +
      "LEFT JOIN \n" +
      "    currentaccount c ON u.id = c.user_id\n" +
      "LEFT JOIN \n" +
      "    loanaccount l ON u.id = l.user_id\n" +
      "LEFT JOIN \n" +
      "    shareaccount sa ON u.id = sa.user_id\n" +
      "WHERE u.org_id = :orgId\n" +
      "GROUP BY \n" +
      "    u.id;";
    @Query(value = ALL_ACCOUNTS_SUM, nativeQuery = true)
    public List<Object[]> getAllAccountsBalanceSum(long orgId);
}
