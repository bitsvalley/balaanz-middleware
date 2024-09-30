package com.bitsvalley.micro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ReportsResponseModel {
  Map<String, Object> data;
  List<CategorizedLedgerAccount> categorizedLedgerAccount;
  List<PrioritizedIssues> prioritizedIssues;
  IssuesByDateStatusMap issuesByDateStatusMap;
  ReportsRequest reportsRequest;
  Map<String, Integer> transactions;
}
