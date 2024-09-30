package com.bitsvalley.micro.model.response;

import com.bitsvalley.micro.webdomain.SavingBilanzList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailySavingAccountResponse {
  private SavingBilanzList savingBilanzList;
  private String amount;
  private long customerId;
  private String orgId;
  private String balance;
}
