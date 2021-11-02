package com.sho.stock.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "history_data")
@Data
public class HistoryData {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String date;
  private Double open;
  private Double high;
  private Double close;
  private Double low;
  private Double volume;
  private Double price_change;
  private Double p_change;
  private Double ma5;
  private Double ma10;
  private Double ma20;
  private Double v_ma5;
  private Double v_ma10;
  private Double v_ma20;
  private Double turnover;
  private String code;
  private String name;
}
