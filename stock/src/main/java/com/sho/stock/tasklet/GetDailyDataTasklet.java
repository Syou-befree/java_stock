package com.sho.stock.tasklet;

import com.sho.stock.model.DailyData;
import com.sho.stock.model.HistoryData;
import com.sho.stock.model.Stock;
import com.sho.stock.repository.HistoryDataRepository;
import com.sho.stock.repository.StockRepository;
import com.sho.stock.util.HttpApi;
import lombok.var;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class GetDailyDataTasklet implements Tasklet {
  
  @Autowired
  private StockRepository stockRepository;
  
  @Autowired
  private HistoryDataRepository historyDataRepository;
  
  @Autowired
  private HttpApi api;
  
  private static final String urlSh = "https://hq.sinajs.cn/list=sh";
  private static final String urlSz = "https://hq.sinajs.cn/list=sz";
  
  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    
    List<Stock> stockList = stockRepository.findAll();
    List<DailyData> dailyDataList = new ArrayList<>();
    List<HistoryData> historyDataList = new ArrayList<>();
    String response = "";
    String data = "";
    
    for (Stock stock : stockList) {
      if (stock.getStockNo().startsWith("6")) {
        response = api.doGet(urlSh + stock.getStockNo());
      } else {
        response = api.doGet(urlSz + stock.getStockNo());
      }
      
      data = response.substring(response.indexOf("=") + 2, response.indexOf(";") - 1);
      
      System.out.println(data);
      
      HistoryData historyData = setHistoryData(stock, data);
      historyDataList.add(historyData);
    }
    
    historyDataRepository.saveAll(historyDataList);
    
    System.out.println("输入完毕");
    
    return RepeatStatus.FINISHED;
  }
  
  private HistoryData setHistoryData(Stock stock, String data) throws Exception {
    var dataObject = data.split(",");
    DailyData dailyData = new DailyData();
    HistoryData historyData = new HistoryData();
    historyData.setDate(dataObject[30]);
    historyData.setOpen(Double.valueOf(dataObject[1]));
    historyData.setHigh(Double.valueOf(dataObject[4]));
    historyData.setClose(Double.valueOf(dataObject[3]));
    historyData.setLow(Double.valueOf(dataObject[5]));
    historyData.setVolume(Double.valueOf(dataObject[8]));
    historyData.setPrice_change(Double.valueOf(dataObject[3]) - Double.valueOf(dataObject[1]));
    historyData.setP_change(Double.valueOf(dataObject[3]) - Double.valueOf(dataObject[1]) / Double.valueOf(dataObject[1]));
    
    
    historyData.setCode(stock.getStockNo());
    historyData.setName(stock.getStockName());
    
    return historyData;
  }
}
