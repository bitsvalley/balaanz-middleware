package com.bitsvalley.micro.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetails {
  private long id;
  private String userName;
  private String customerNumber;
  private String dailyCustomerNumber;
  private String firstName;
  private String lastName;
  private long orgId;
  private double unsignedAmountLimit;
}
