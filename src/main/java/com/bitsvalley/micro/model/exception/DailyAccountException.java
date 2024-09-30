package com.bitsvalley.micro.model.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DailyAccountException extends RuntimeException{
  private String ERROR_CODE = "DAILY_ACCOUNT_ERROR";
  public DailyAccountException(String message){
    super(message);
  }
  public DailyAccountException(String message, String errorcode){
    super(message);
    this.ERROR_CODE = errorcode;
  }
}
