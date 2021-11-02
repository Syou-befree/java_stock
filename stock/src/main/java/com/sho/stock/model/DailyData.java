package com.sho.stock.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_data")
@Data
public class DailyData {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  // 股票代码
  private String stockNo;
  // 股票名
  private String stockName;
  // 当日开盘价
  private String todayStartPrice;
  // 昨天收盘价
  private String yesterdayEndPrice;
  // 当前价格
  private String currentPrice;
  // 当日最高价
  private String highestPrice;
  // 当日最低价
  private String lowestPrice;
  // 买一报价
  private String competeBuyPrice;
  // 卖一报价
  private String competeSellPrice;
  // 当日成交股票数
  private int dealedStockNumber;
  // 当日成交金额
  private int dealedAmount;
  // 买一股数
  private int buyOneGushu;
  // 买一报价
  private String buyOnePrice;
  // 买二股数
  private int buyTwoGushu;
  // 买二报价
  private String buyTwoPrice;
  // 买三股数
  private int buyThreeGushu;
  // 买三报价
  private String buyThreePrice;
  // 买四股数
  private int buyFourGushu;
  // 买四报价
  private String buyFourPrice;
  // 买五股数
  private int buyFiveGushu;
  // 买五报价
  private String buyFivePrice;
  // 卖一股数
  private int sellOneGushu;
  // 卖一报价
  private String sellOnePrice;
  // 卖二股数
  private int sellTwoGushu;
  // 卖二报价
  private String sellTwoPrice;
  // 卖三股数
  private int sellThreeGushu;
  // 卖三报价
  private String sellThreePrice;
  // 卖四股数
  private int sellFourGushu;
  // 卖四报价
  private String sellFourPrice;
  // 卖五股数
  private int sellFiveGushu;
  // 卖五报价
  private String sellFivePrice;
  // 当日日期
  private String date;
  // 当日时间
  private String time;
  // 脚本执行日期时间
  private Date insertDate;
}
