package com.bitsvalley.micro.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceMailRequest {
  private String customer;
  private double amount;
  private String transactionId;
  private String dateTime;
  private String agentName;
  private String businessName;
  private String address;
  private String telephone;
  private String email;
}
