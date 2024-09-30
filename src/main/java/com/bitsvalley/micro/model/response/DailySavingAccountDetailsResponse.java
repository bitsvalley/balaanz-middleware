package com.bitsvalley.micro.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DailySavingAccountDetailsResponse {
  private String id;
  private String accountBalance;
  private String accountMinimumBalance;
  private String accountNumber;
  private String active;
  private String branchCode;
  private String country;
  private String createdBy;
  private String createdDate;
  private String interestRate;
  private String lastUpdatedDate;
  private String minimumPayment;
  private String orgId;
  private String productCode;
  private String userId;
}
