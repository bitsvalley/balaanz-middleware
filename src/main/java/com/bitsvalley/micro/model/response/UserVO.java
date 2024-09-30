package com.bitsvalley.micro.model.response;

import com.bitsvalley.micro.utils.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVO {

  private long id;
  private long orgId;
  private String userName;
  private String firstName;
  private String lastName;
  private String gender;
  private String referral;
  private String email;
  private Date created;
  private String createdBy;
  private AccountStatus accountStatus;
  private double unsignedAmount;
  private double collectionLimit;
  private String telephone1;

  private String telephone2;
  private String address;
  private String password;
  private String notes;

  private String dateOfBirth;
  private String idFilePath;
  private String idFilePath2;
  private String idFilePath3;
  private String idFilePath4;
  private String identityCardNumber;

  private String terminalCode = "000000";
  private boolean receiveEmailNotifications;

  private LocalDateTime accountExpiredDate;
  private boolean accountLocked;

  private LocalDateTime accountBlockedDate;
  private boolean accountExpired;
  private String identityCardExpiry;
  private Date lastUpdated;

}
