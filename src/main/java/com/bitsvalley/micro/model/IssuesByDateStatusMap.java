package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Setter
@Getter
public class IssuesByDateStatusMap {
  private Map<LocalDate, Map<String, Integer>> issuesByDateAndStatus;
}
