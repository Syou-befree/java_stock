package com.sho.stock.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stock")
@Data
public class Stock {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String stockNo;
  private String stockName;
}
