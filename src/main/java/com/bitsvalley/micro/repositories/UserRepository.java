package com.bitsvalley.micro.repositories;

import com.bitsvalley.micro.domain.Branch;
import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.utils.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {
    User findByUserName(String userName);


//    @Query(value = "SELECT FROM user sa where sa.username = :userName AND sa.branch.branch_code = :branchCode AND sa.org_id = :orgId", nativeQuery = true)
    User findByUserNameAndBranchAndOrgId(String userName, Branch branch, long orgId);

    ArrayList<User> findByIdentityCardNumber(String identityCardNumber);

    User findByUserNameAndOrgId(String userName, long orgId);

    ArrayList<User> findByOrgId(long orgId);

    ArrayList<User> findByOrgIdAndBranch(long orgId, Branch branch);

    ArrayList<User> findByOrgIdAndId(long orgId, long id);

    ArrayList<User> findByOrgIdAndAccountStatus(long orgId, AccountStatus status);

    ArrayList<User> findAllByUserRoleInAndOrgId(ArrayList<UserRole> userRole, Long orgId);

    ArrayList<User> findAllByUserRoleInAndOrgIdAndAccountStatus(ArrayList<UserRole> userRole, Long orgId, AccountStatus accountStatus);
//    ArrayList<User> findAllByUserRoleInAndOrgIdAndAccountStatus(ArrayList<UserRole> userRole, Long orgId, AccountStatus accountStatus);

    ArrayList<User> findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(ArrayList<UserRole> userRole, Long orgId, AccountStatus accountStatus, Branch branch);

    ArrayList<User> findDistintAllByUserRoleNotInAndOrgId(ArrayList<UserRole> userRole, Long orgId);

//    @Query(value = "SELECT count(*) FROM user la WHERE la.userRole IN (:role) AND la.orgId = :orgId", nativeQuery = true)
//    int countByUserRole(String role, long orgId);

    //    @Query(value = "SELECT COUNT(*) AS numberOfLoanAccount FROM loanaccount la where la.branch_code = :branchCode and la.org_id = :orgId", nativeQuery = true)
    String ACTIVE_USERS = "SELECT COUNT(*) FROM user " +
            "WHERE ACCOUNT_EXPIRED = false AND customer_number is null AND CREATED >=:fromDate AND CREATED <=:toDate" +
            " AND ORG_ID=:orgId";

    @Query(value = ACTIVE_USERS, nativeQuery = true)
    Integer findActiveUsers(@Param(value = "fromDate") LocalDateTime fromDate,
                            @Param(value = "toDate") LocalDateTime toDate,
                            @Param(value = "orgId") Integer orgId);

    String UNLOCKED_USERS = "SELECT COUNT(*) FROM user WHERE ACCOUNT_LOCKED = false AND org_id=:orgId";

    @Query(value = UNLOCKED_USERS, nativeQuery = true)
    Integer findUnlockedUsers(@Param(value = "orgId") Integer orgId);

    String FIND_USERS = "SELECT COUNT()";

    @Query(value = FIND_USERS, nativeQuery = true)
    void findUsers();

    String TOTAL_CUSTOMERS = "select count(*) from user " +
            "where org_id=:orgId AND customer_number is not null AND CREATED >=:fromDate AND CREATED <=:toDate";

    @Query(value = TOTAL_CUSTOMERS, nativeQuery = true)
    Integer findCustomers(@Param(value = "fromDate") LocalDateTime fromDate,
                          @Param(value = "toDate") LocalDateTime toDate,
                          @Param(value = "orgId") int orgId);

    String TOTAL_USERS = "SELECT COUNT(*) FROM user";

    @Query(value = TOTAL_USERS, nativeQuery = true)
    Integer usersCount();

//    @EntityGraph(attributePaths = {"User"})
    List<User> findByOrgIdAndCreatedByAndUserRole(long orgId, String username, UserRole role);
}
