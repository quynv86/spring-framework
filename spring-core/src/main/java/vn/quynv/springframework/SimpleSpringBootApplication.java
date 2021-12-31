package vn.quynv.springframework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class SimpleSpringBootApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SimpleSpringBootApplication.class, args);
    }

    final int DEFAULT_INTERVAL = 10;
    @Bean
    CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                int logInterval = DEFAULT_INTERVAL;
                if(args!=null && args.length>0) {
                    logInterval = Integer.parseInt(args[1]);
                }else {
                    log.info("The log interval is not set. Fall back to default value {}", DEFAULT_INTERVAL );
                }
                if(logInterval<1) {
                    logInterval =  DEFAULT_INTERVAL;
                }
                log.info("Print log interval in {} seconds", logInterval);

                while(1==1) {
                    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    final int LOG_LEVEL = randomNumber(4);
                    final int TRANS_COUNT = randomNumber(10);
                    switch (LOG_LEVEL) {
                        case 0 : {
                            log.info("The Time is : {}", now);
                            log.info("Total Trans: {}", TRANS_COUNT);
                            break;
                        }
                        case 1: {
                            log.warn("The Time is : {}", now);
                            log.warn("Total Trans: {}", TRANS_COUNT);
                            break;
                        } case 2: {
                            log.error("The Time is : {}", now);
                            log.error("Total Trans: {}", TRANS_COUNT);
                            break;
                        }
                        default: {
                            log.debug ("The Time is : {}", now);
                            log.debug("Total Trans: {}", TRANS_COUNT);
                        }
                    }
                    safeDelay(logInterval);
                }
            }
            private void safeDelay(int delayTimeInSeconds) throws InterruptedException{
                   int current = 1;
                   do {
                       Thread.sleep(1_000);
                   }while((current++) <delayTimeInSeconds);
            }

            private int randomNumber(int max) {
                return (int)(Math.random() * max);
            }
        };
    }
}
