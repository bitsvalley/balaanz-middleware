package com.bitsvalley.micro.model.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceException {
  private String message;
  private String remarks;
  private String status;
  private String errorCode;
}
