package com.bitsvalley.micro.model.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRootException extends RuntimeException{

  private String errorCode = "User_Root_Error";

  public UserRootException(String errorCode, String message){
    super(message);
    this.errorCode = errorCode;
  }
}
