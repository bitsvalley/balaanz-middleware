package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategorizedLedgerAccount {
  private String category;
  private Integer totalCount;
  private Integer active;
  private Integer inactive;
}
