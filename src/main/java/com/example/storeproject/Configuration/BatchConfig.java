package com.example.storeproject.Configuration;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.models.User;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
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
}