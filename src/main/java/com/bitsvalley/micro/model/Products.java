package com.bitsvalley.micro.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Products {

  private String name;
  private Date createdDate;
  private Date lastUpdatedDate;

  private long category;

  private double unitPrice;
  private double bulkPrice;
  private double purchasePrice;
  private double MSRP;

  private int stockAmount;

  private String image1;
  private String image2;
  private String image3;
  private String image4;

  private String code;
  private String barcode;
  private String expiry;

  private String shortDescription;
  private String longDescription;
  private boolean online;
  private boolean active;

}
