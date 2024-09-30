package com.bitsvalley.micro.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DailySavingRequest {
  private String interest;
  private String amountOnHold;
  private String minimumDeposit;
  private String notes;
  private long customerId;
}
