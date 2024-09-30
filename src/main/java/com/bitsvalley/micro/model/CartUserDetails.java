package com.bitsvalley.micro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartUserDetails{
  private String name;
  private String address;
  private String contactNo;
  private String company;
  private String invoiceNo;
}
