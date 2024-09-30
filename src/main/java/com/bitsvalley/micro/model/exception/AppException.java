package com.bitsvalley.micro.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppException extends Exception {

  private String errorCode = "User_Root_Error";

  public AppException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
}
