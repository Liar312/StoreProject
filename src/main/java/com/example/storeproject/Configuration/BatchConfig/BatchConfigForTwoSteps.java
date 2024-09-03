package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.models.Product;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
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
public class BatchConfigForTwoSteps {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;

    public BatchConfigForTwoSteps(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.dataSource = dataSource;
    }

    @Bean
    public MultiResourceItemReader<Product> multiResourceItemReaderProduct() {
        MultiResourceItemReader<Product> reader = new MultiResourceItemReader<>();
        reader.setResources(new ClassPathResource[] {
                new ClassPathResource("users1.csv"),
                new ClassPathResource("users2.csv")
        });
        //reader.setDelegate(flatFileItemReader());
        return reader;
    }


    @Bean
    public ProductProcessor productProcessor() {
        return new ProductProcessor();
    }

    @Bean
    public CacheUserProcessor cacheUserProcessor() {
        return new CacheUserProcessor();
    }
    @Bean
    public UserProcessor userProcessor(){
        return new UserProcessor();
    }
    @Bean
    public JdbcBatchItemWriter<Product> productWriter(){
        JdbcBatchItemWriter<Product> productJdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        productJdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());//указывает что данные будут извлечены из product этим методом
    productJdbcBatchItemWriter.setSql("INSERT INTO products (id, title, author, city) VALUES (:id, :title, :author, :city)");
    productJdbcBatchItemWriter.setDataSource(this.dataSource);
    return productJdbcBatchItemWriter;
    }
    @Bean
    public Step step2() {
        return new StepBuilder("step1",jobRepository)
                .<Product,Product>chunk(10,platformTransactionManager)//10 записей-одна транзакция
                .reader(multiResourceItemReaderProduct())
                .processor(productProcessor())
                .writer(productWriter())
                .build();

    }



}
