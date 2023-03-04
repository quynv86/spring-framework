package vn.quynv.springframework.job.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vn.quynv.springframework.domain.transaction.Transaction;

import java.io.IOException;

@Configuration
@Slf4j
public class PartitionerJobConf {

    ResourcePatternResolver resoursePatternResolver;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public PartitionerJobConf(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ResourcePatternResolver resoursePatternResolver) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.resoursePatternResolver = resoursePatternResolver;
    }

    @Bean(name="partitionerJob")
    public Job partitionerJob(){
        return jobBuilderFactory.get("partitionerJob-2")
                .start(partitionStep())
                .build();
    }

    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("slaveStep", partitioner())
                .step(slaveStep())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step slaveStep()  {
        return stepBuilderFactory.get("slaveStep")
                .<Transaction, Transaction>chunk(1)
                .reader(itemReader(null))
                .writer(itemWriter())
                .build();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<Transaction> itemReader (@Value("#{stepExecutionContext[fileName]}") String filename) {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"transId","amount"};
        tokenizer.setNames(tokens);
        reader.setResource(new ClassPathResource("input/partitioner/" + filename));
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        reader.setLinesToSkip(0);
        reader.setLineMapper(lineMapper);
        return reader;
    }
    @Bean
    public FieldSetMapper fieldSetMapper() {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Transaction.class);
        return fieldSetMapper;
    }

    @Bean
    @StepScope
    public ItemWriter<Transaction> itemWriter()  {
        return new LoggingItemWriter();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public CustomMultiResourcePartitioner partitioner() {
        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
        Resource[] resources;
        try {
            resources = resoursePatternResolver.getResources("file:spring-batch-job/src/main/resources/input/partitioner/*.csv");
            log.info("Resource length: {}", resources.length);
        } catch (IOException e) {
            throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
        }
        partitioner.setResources(resources);
        return partitioner;
    }
}
