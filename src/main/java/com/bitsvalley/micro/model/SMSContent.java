package com.bitsvalley.micro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SMSContent {

  private String sendTo;
  private String name;
  private String amount;
  private String transactType;
  private String businessName;
  private String accountNumber;
  private String orgName;
  private String balance;
}
