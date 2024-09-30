package com.bitsvalley.micro.advice;

import com.bitsvalley.micro.model.exception.AppException;
import com.bitsvalley.micro.model.exception.BalanceException;
import com.bitsvalley.micro.model.exception.DailyAccountException;
import com.bitsvalley.micro.model.exception.UserRootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
@Configuration
@Slf4j
public class RestControllerAdvice {

  @ExceptionHandler(DailyAccountException.class)
  public ResponseEntity<?> dailyAccountException(DailyAccountException exception){
    log.error("Error : ",exception);
    return
      new ResponseEntity<>(BalanceException.builder()
        .errorCode(exception.getERROR_CODE())
        .message(exception.getMessage())
        .remarks(exception.getMessage())
        .status("20001").build(),
        HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception exception){
    log.error("Error : ",exception);
    return
      new ResponseEntity<>(BalanceException.builder()
        .errorCode("GENERIC_EXCEPTION")
        .message("Something went wrong")
        .remarks("")
        .status("50001").build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(UserRootException.class)
  public ResponseEntity<?> userRootException(UserRootException exception){
    log.error("Error : ",exception);
    return
      new ResponseEntity<>(BalanceException.builder()
        .errorCode(exception.getErrorCode())
        .message(exception.getMessage())
        .remarks("")
        .status("40001").build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<?> appException(AppException exception) {
    log.error("Error : ", exception);
    return
        new ResponseEntity<>(BalanceException.builder()
            .errorCode(exception.getErrorCode())
            .message(exception.getMessage())
            .remarks("")
            .status("40004").build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
