package com.bitsvalley.micro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PosReceiptPdf {
  private CartUserDetails cartUserDetails;
  private List<CartDetail> cartDetails;
  private int quantity;
  private double totalAmount;
  private double totalTax;
  private double subTotal;
  private String logoPath;
  private String slogan;
}

