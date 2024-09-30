package com.bitsvalley.micro.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyStats {
  private int withdrawDeposit;
  private double savingAmount;
  private double unsignedAmount;
}
