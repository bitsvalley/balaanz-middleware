package com.bitsvalley.micro.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class CreateUserPayload {
  private String userRole = "ROLE_CUSTOMER";
  private String userName;
  private String firstName = "User_"+System.currentTimeMillis();
  private String lastName;
  private String gender;
  private String profession;
  private String dateOfBirth;
  private String password;
  private String identityCardNumber = ""+System.currentTimeMillis();
  private String identityCardExpiry;
  private String referral;
  private String email;
  private String address;
  private String telephone1;
  private String telephone2;
  private boolean accountLocked;
  private String createBy;
  private boolean sendEmail;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
