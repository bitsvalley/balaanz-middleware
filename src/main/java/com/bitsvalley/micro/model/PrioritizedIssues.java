package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PrioritizedIssues {
  private Integer priority;
  private Integer totalCount;
  private Integer assigned;
  private Integer unassigned;
}
