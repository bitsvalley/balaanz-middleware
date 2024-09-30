package com.bitsvalley.micro.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LedgerCategory {

  public LedgerCategory(){

  }
  public static Map<String, String> fetchLedgerCategory(){
    Map<String, String> ledgerCategory = new HashMap<>();
    ledgerCategory.put("1000 – 1999", "ASSETS");
    ledgerCategory.put("2000 – 2999", "LONG_TERM_ASSETS");
    ledgerCategory.put("3000 – 3999", "INVENTORY");
    ledgerCategory.put("4000 – 4999", "THIRD_PARTY_REVENUE");
    ledgerCategory.put("5000 – 5999", "TREASURY_ACCOUNTS");
    ledgerCategory.put("6000 – 6999", "EXPENSE");
    ledgerCategory.put("7000 – 7999", "REVENUE");
    ledgerCategory.put("8000 – 8999", "EXTRA_ORDINARY_ACTIVITIES");
    return ledgerCategory;
  }
}
