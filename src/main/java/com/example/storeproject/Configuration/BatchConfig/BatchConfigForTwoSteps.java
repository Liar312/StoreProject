package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.models.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Comparator;

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
        reader.setResources(new ClassPathResource[]{
                new ClassPathResource("users1.csv"),
                new ClassPathResource("users2.csv")
        });
        //reader.setDelegate(flatFileItemReader());
        return reader;
    }

    @Bean
    public MultiResourceItemReader<CacheUser> multiResourceItemReaderUser() {
        MultiResourceItemReader<CacheUser> reader = new MultiResourceItemReader<>();
        reader.setComparator(new Comparator<Resource>() {
            @Override
            public int compare(Resource resource1, Resource resource2) {
                try {
                    return resource1.getFilename().compareTo(resource2.getFilename());
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка сравнения ресурсов", e);
                }

            }
        });
        reader.setResources(new ClassPathResource[]{
                new ClassPathResource("User1.csv"),
                new ClassPathResource("User2.csv")
        });
        return reader;
    }


    @Bean
    public ProductProcessor productProcessor() {
        return new ProductProcessor();
    }

    //    @Bean
//    public CacheUserProcessor cacheUserProcessor() {
//        return new CacheUserProcessor();
//    }
    @Bean
    public UserProcessor userProcessor() {
        return new UserProcessor();
    }

    @Bean
    @Qualifier("writer")
    public JdbcBatchItemWriter<CacheUser> productWriter() {
        JdbcBatchItemWriter<CacheUser> productJdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        productJdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());//указывает что данные будут извлечены из product этим методом
        productJdbcBatchItemWriter.setSql("INSERT INTO products (id, title, author, city) VALUES (:id, :title, :author, :city)");
        productJdbcBatchItemWriter.setDataSource(this.dataSource);
        return productJdbcBatchItemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<Product> userWriter() {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO CacheUsers (id, name,email) VALUES (:id, :firstName, :email)");
        writer.setDataSource(this.dataSource);
        return writer;
    }

    @Bean
    public CacheUserProcessor cacheUserProcessor() {
        return new CacheUserProcessor();
    }

    @Bean

    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .<Product, CacheUser>chunk(10, platformTransactionManager)
                .reader(multiResourceItemReaderProduct())
                .processor(cacheUserProcessor())
                .writer(productWriter())
                .build();
    }

    @Bean
    public Step step1(){
        return new StepBuilder("step1",jobRepository)
                .<CacheUser,Product>chunk(10,platformTransactionManager)
                .reader(multiResourceItemReaderUser())
                .processor(productProcessor())
                .writer(userWriter())
                .build();
    }
    @Bean
    public Job importUserJob(JobCompletionNotificationListener jobExecution){
        return new JobBuilder("ImportUserJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecution)
                .flow(step1())
                .next(step2())
                .end()
                .build();
    }
}
