package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserReportsModel {
  private String attribute;
  private Integer value;
}
