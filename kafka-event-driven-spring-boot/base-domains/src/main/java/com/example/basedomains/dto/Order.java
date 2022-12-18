package com.example.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private String orderId;
  private String name;
  private int quantity;
  private double price;
}