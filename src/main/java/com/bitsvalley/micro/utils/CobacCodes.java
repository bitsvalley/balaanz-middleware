package com.bitsvalley.micro.utils;

import java.util.HashMap;
import java.util.Map;

public class CobacCodes {

  public static Map<String, String> cobacCodes(){
    Map<String, String> codes = new HashMap<>();
    codes.put("CAPITAL ACCOUNTS (1000 – 1999)","1");
    codes.put("FIXED ASSET ACCOUNTS (2000 – 2999)", "2");
    codes.put("CUSTOMER ACCOUNTS (3000 – 3999)", "3");
    codes.put("THIRD PARTY ACCOUNTS AND ACCRUALS(4000 – 4999)", "4");
    codes.put("TREASURY AND INTERBANK OPERATIONS (5000 – 5999)","5");
    codes.put("EXPENSE ACCOUNTS (6000 – 6999)", "6");
    codes.put("REVENUE ACCOUNTS (7000 – 7999)", "7");
    codes.put("INTERMEDIATE MANAGEMENT ACCOUNTS (8000 – 8999)", "8");
    codes.put("OFF BALANCE SHEET ACCOUNTS", "9");
    return codes;
  }
}
