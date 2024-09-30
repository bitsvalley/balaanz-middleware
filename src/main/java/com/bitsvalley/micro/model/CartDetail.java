package com.bitsvalley.micro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetail{
  private String id;
  private String productName;
  private double price;
  private int quantity;
  private float tax;
  private double total;
}
