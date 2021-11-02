package com.sho.stock.tasklet;

import com.sho.stock.model.Stock;
import com.sho.stock.repository.HistoryDataRepository;
import com.sho.stock.repository.StockRepository;
import lombok.var;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class GetMaTasklet implements Tasklet {
  
  @Autowired
  private StockRepository stockRepository;
  
  @Autowired
  private HistoryDataRepository historyDataRepository;
  
  private List<Stock> stockList;
  
  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    
    stockList = stockRepository.findAll();
    
    for (Stock stock : stockList) {
      Double sum144 = 0.00;
      Double sum144jian1 = 0.00;
      Double sum169 = 0.00;
      Double sum169jian1 = 0.00;
      Double sum288 = 0.00;
      Double sum338 = 0.00;
      
      Double ma144 = 0.00;
      Double ma144jian1 = 0.00;
      Double ma169 = 0.00;
      Double ma169jian1 = 0.00;
      Double ma288 = 0.00;
      Double ma338 = 0.00;
      
      if (!stock.getStockName().contains("ST")) {
//        System.out.println("现在股票代码是：" + stock.getStockName() + "  股票名字:" + stock.getStockName());
        var historyDataList = historyDataRepository.findByCodeOrderByDateDesc(stock.getStockNo());
        if (historyDataList.size() >= 338) {
          for (int i = 0; i < 144; i++) {
            sum144 = sum144 + historyDataList.get(i).getClose();
          }
          for (int i = 1; i < 145; i++) {
            sum144jian1 = sum144jian1 + historyDataList.get(i).getClose();
          }
          for (int i = 144; i < 169; i++) {
            sum169 = sum144 + historyDataList.get(i).getClose();
          }
          for (int i = 1; i < 145; i++) {
            sum169jian1 = sum169jian1 + historyDataList.get(i).getClose();
          }
          for (int i = 169; i < 288; i++) {
            sum288 = sum169 + historyDataList.get(i).getClose();
          }
          for (int i = 288; i < 338; i++) {
            sum338 = sum288 + historyDataList.get(i).getClose();
          }
          ma144 = sum144 / 144;
          ma144jian1 = sum144jian1 / 144;
          ma169 = sum169 / 169;
          ma169jian1 = sum169jian1 / 169;
          ma288 = sum288 / 288;
          ma338 = sum338 / 338;
          
          if (ma288 > ma338 && ma338 > ma169 && ma169 < ma144 && ma144jian1 < ma169jian1) {
            System.out.println("股票代码：" + stock.getStockNo());
          }
        }
      }
    }
    return RepeatStatus.FINISHED;
  }
}
