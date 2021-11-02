package com.sho.stock.batch;

import com.sho.stock.listener.JobListener;
import com.sho.stock.tasklet.GetDailyDataTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DataBatchConfiguration {
  private static final Logger log = LoggerFactory.getLogger(DataBatchConfiguration.class);
  
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  
  @Autowired
  private JobListener jobListener;
  
  //    @Autowired
//    private GetAllStockTasklet getAllStockTasklet;
//
  @Autowired
  private GetDailyDataTasklet getDailyDataTasklet;

//  @Autowired
//  private GetMaTasklet getMaTasklet;
  
  @Bean
  public Job dataHandleJob() {
    return jobBuilderFactory.get("dataHandleJob").
            incrementer(new RunIdIncrementer()).
            start(testTaskletStep()).
            listener(jobListener).
            build();
  }
  
  @Bean
  public Step testTaskletStep() {
    return stepBuilderFactory.get("testTaskletStep")
            .tasklet(getDailyDataTasklet)
            .build();
  }
}
