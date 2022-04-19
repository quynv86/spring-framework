package vn.quynv.springframework.job.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Configuration
@Slf4j
public class TimebaseJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job timeBaseJob (@Autowired Step step) {
        return jobBuilderFactory.get("timebase-job").start(step).build();
    }

    public TimebaseJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }
    @Bean
    public Step simpleStep() {
       return stepBuilderFactory.get("print-current-time")
                .tasklet((stepContribution, chunkContext) -> {
                    String partnerCode = (String)chunkContext.getStepContext().getJobParameters().get("partner-code");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                    String currentTime = dateFormat.format(new Date());
                    log.info("Process partner: {}. The current time is: {}",partnerCode, currentTime);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
