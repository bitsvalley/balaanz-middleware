package com.bitsvalley.micro.model.requests;

import com.bitsvalley.micro.domain.DailySavingAccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DailySavingAccountRequest {
  private long userId;
  private String accountOwner;
  private String representative;
  private String dailySavingAccountId;
  private String transactionType;
  private boolean isBillSectionEnabled;
  private long customerId;

  private String modeOfPayment;
  private double savingAmount;
  private String notes;

  private int tenThousand;
  private int fiveThousand;
  private int twoThousand;
  private int oneThousand;
  private int fiveHundred;
  private int oneHundred;
  private int fifty;
  private int twentyFive;
  private int ten;
  private int five;
  private int one;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
