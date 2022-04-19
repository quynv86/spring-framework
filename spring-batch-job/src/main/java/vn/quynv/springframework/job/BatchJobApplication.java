package vn.quynv.springframework.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class BatchJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchJobApplication.class, args);
    }

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("partitionerJob")
    Job partitionerJob;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            jobLauncher.run(partitionerJob, new JobParametersBuilder().addLong("Current-Time", System.currentTimeMillis())
                    .toJobParameters());
        };
    }

}
