package com.example.storeproject.Configuration.BatchConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Starting{}",jobExecution.getJobInstance().getJobName());//получаем имя текущей джобы

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
      if(jobExecution.getStatus().isUnsuccessful()){
          System.err.println("Job failed with status"+jobExecution.getStatus());
      }
      else {
          System.out.println("Job complete successfully");
      }
    }
}
