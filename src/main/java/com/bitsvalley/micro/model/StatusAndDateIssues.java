package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class StatusAndDateIssues {
  private LocalDate date;
  private Integer status;
  private Integer count;
}
