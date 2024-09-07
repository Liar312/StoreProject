package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<CacheUser> reader() {
        FlatFileItemReader<CacheUser> flatFileItemReader = new FlatFileItemReader<>();
        reader().setResource(new ClassPathResource("CacheUser.csv"));//указываем путь ридеру
        reader().setLinesToSkip(1);
        reader().setLineMapper(new DefaultLineMapper<CacheUser>() {{ //задаем лайнмапер который мапит входящие файлы в нашу сущность
            setLineTokenizer(new DelimitedLineTokenizer() {{//внутри маппера токенайзер разбивает строку на токены по соответсвующим именам
                setNames("id", "name", "email");//первый токен будет Id
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CacheUser>() {{
                setTargetType(CacheUser.class);
            }});
        }});
        return flatFileItemReader;
    }
   @Bean
    public UserProcessor processor(){
        return new UserProcessor(); //здесь прогоняем при надобности сущность через процессор для ее видоизменения
   }
   @Bean//запись в бд в пакетном режиме
    public JdbcBatchItemWriter<CacheUser> writer(){
        JdbcBatchItemWriter<CacheUser> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());//источник sql параметров,берет свойства объекта cacheUser и передает в sql запрос
       writer.setSql("INSERT INTO users (id, name,email) VALUES (:id, :firstName, :email)");
       writer.setDataSource(this.dataSource);
       return writer;
   }
   @Bean
    public Job importUserJob(JobCompletionNotificationListener jobExecution){
        return new JobBuilder("ImportUserJob",jobRepository)
                .incrementer(new RunIdIncrementer())//инкриментор для уникальных идентификаторов джоба
                .listener(jobExecution)//слушателя используем для получения логов
                .flow(step1())
                .end()
                .build();
   }
   @Bean
    public Step step1() {
       return new StepBuilder("step1",jobRepository)
               .<CacheUser,CacheUser>chunk(10,platformTransactionManager)//10 записей-одна транзакция
               .reader(reader())
               .processor(processor())
               .writer(writer())
               .build();

   }

}