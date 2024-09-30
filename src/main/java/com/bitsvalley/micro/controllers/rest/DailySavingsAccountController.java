package com.bitsvalley.micro.controllers.rest;

import com.bitsvalley.micro.model.requests.DailySavingAccountRequest;
import com.bitsvalley.micro.model.requests.DailySavingRequest;
import com.bitsvalley.micro.model.response.DailySavingAccountDetailsResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountTransactions;
import com.bitsvalley.micro.model.response.DailyStats;
import com.bitsvalley.micro.services.DailySavingsAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dailySavingAccount")
@Slf4j
@RequiredArgsConstructor
public class DailySavingsAccountController {

  private final DailySavingsAccountService dailySavingsAccountService;
  /**
  *
   * Get current user (logged in)
   * required details from web ui
   * - account owner
   * - get saving amount and check with min balance
   *
  * */
  @PostMapping("/registerDailySavingAccountTransactionForm")
  public ResponseEntity<DailySavingAccountResponse> dailySavingAccountTransaction(
    @Validated @RequestBody DailySavingAccountRequest request
  ){
    log.info("register daily account transaction");
    DailySavingAccountResponse dailySavingAccountResponse = dailySavingsAccountService.registerTransaction(request);
    return new ResponseEntity<>(dailySavingAccountResponse, HttpStatus.OK);
  }

  @GetMapping("/details/{userId}")
  public ResponseEntity<List<DailySavingAccountDetailsResponse>> getDetails(@PathVariable String userId){
    log.info("Daily saving account details request: {}", userId);
    List<DailySavingAccountDetailsResponse> dailySavingAccountDetailsResponses = dailySavingsAccountService.fetchDetails(userId);
    return new ResponseEntity<>(dailySavingAccountDetailsResponses, HttpStatus.OK);
  }

  @GetMapping("/transactions/{accountId}")
  public ResponseEntity<List<DailySavingAccountTransactions>> getTransactions(
    @PathVariable String accountId,
    @RequestParam("page") int page,
    @RequestParam("size") int size){
    log.info("Daily saving account details request: {}", accountId);
    List<DailySavingAccountTransactions> dailySavingAccountDetailsResponses =
      dailySavingsAccountService.fetchTransactions( accountId, page, size);
    return new ResponseEntity<>(dailySavingAccountDetailsResponses, HttpStatus.OK);
  }
  @GetMapping("/stats")
  public ResponseEntity<Map<String, List<DailyStats>>> agentStats(@RequestParam(name = "userId") String userId){
    Map<String, List<DailyStats>> dailyStats = dailySavingsAccountService.dailyStats(userId);
    return new ResponseEntity<>(dailyStats, HttpStatus.OK);
  }

  @GetMapping("/agentHistory")
  public ResponseEntity<?> agentHistory(@RequestParam(name = "userId") long userId){
    List<DailySavingAccountTransactions> transactions = dailySavingsAccountService.agentlatestTransactions(userId);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }

  @PostMapping("/registerDailySavingAccountForm")
  public ResponseEntity<String> dailySavingAccount(
    @Validated @RequestBody DailySavingRequest request
  ){
    log.info("register daily account : {}",request);
     dailySavingsAccountService.registerDailyAccount(request);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}
