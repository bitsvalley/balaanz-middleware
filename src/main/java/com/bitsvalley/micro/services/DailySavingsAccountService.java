package com.bitsvalley.micro.services;

import com.bitsvalley.micro.model.requests.DailySavingAccountRequest;
import com.bitsvalley.micro.model.requests.DailySavingRequest;
import com.bitsvalley.micro.model.response.DailySavingAccountDetailsResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountTransactions;
import com.bitsvalley.micro.model.response.DailyStats;

import java.util.List;
import java.util.Map;

public interface DailySavingsAccountService {
  DailySavingAccountResponse registerTransaction(DailySavingAccountRequest dailySavingAccountRequest);
  void registerDailyAccount(DailySavingRequest dailySavingAccountRequest);
  List<DailySavingAccountDetailsResponse> fetchDetails(String id);
  List<DailySavingAccountTransactions> fetchTransactions(String accountId, int page, int size);
  Map<String, List<DailyStats>> dailyStats(String accountId);
  List<DailySavingAccountTransactions> agentlatestTransactions(long accountId);

  List<DailySavingAccountTransactions> agentTransactions(long accountId, String transactionType);
}
