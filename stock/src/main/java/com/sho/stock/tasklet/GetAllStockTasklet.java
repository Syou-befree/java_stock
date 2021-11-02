package com.sho.stock.tasklet;

import com.sho.stock.model.Stock;
import com.sho.stock.repository.StockRepository;
import com.sho.stock.util.HttpApi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class GetAllStockTasklet implements Tasklet {
  
  @Autowired
  private StockRepository stockRepository;
  
  @Autowired
  private HttpApi api;
  
  private static final String SRC_URL = "http://app.finance.ifeng.com/list/stock.php?t=hs";
  
  private List<Stock> stockList;
  
  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    
    stockList = new ArrayList<Stock>();
    String url = SRC_URL;
    
    int idx = 0;
    while (true) {
      System.out.println(url);
      
      String html = api.doGet(url);
      Document doc = Jsoup.parse(html, "utf-8");
      
      // Find core node
      Element divtab01 = doc.getElementsByClass("tab01").last();
      
      // Find stocks
      Elements trs = divtab01.getElementsByTag("tr");
      for (Element tr : trs) {
        Elements tds = tr.getElementsByTag("td");
        if (tds.size() > 2) {
          Element codeElm = tds.get(0).getElementsByTag("a").last();
          Element nameElm = tds.get(1).getElementsByTag("a").last();
          
          Stock s = new Stock();
          s.setStockNo(codeElm.text());
          s.setStockName(nameElm.text());
          idx++;
          stockList.add(s);
        }
      }
      
      // Find next page url
      Element lastLink = divtab01.getElementsByTag("a").last();
      if (lastLink.text().equals("下一页")) {
        url = "http://app.finance.ifeng.com/list/stock.php" + lastLink.attr("href");
      } else {
        break;
      }
    }
    
    System.out.println("共找到" + idx + "个股票.");
    
    stockRepository.saveAll(stockList);
    
    return RepeatStatus.FINISHED;
  }
}
