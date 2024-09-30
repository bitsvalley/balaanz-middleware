package com.bitsvalley.micro.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportsRequest {
  private LocalDateTime fromDate;
  private LocalDateTime toDate;
  private Integer orgId;
  private Integer branchId;
}
