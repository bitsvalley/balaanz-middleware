package com.bitsvalley.micro.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailySavingAccountTransactions {
  private String id;
  private String accountBalance;
  private String accountOwner;
  private String branch;
  private String createdDate;
  private String orgId;
  private String reference;
  private String savingAmount;
  private String dailySavingAccountId;
  private String transactionType;

  @JsonProperty("createdBy")
  private String createdBy;

  private String transActionStatus;
}
