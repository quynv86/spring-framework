package vn.quynv.springframework;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class RestApiApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(RestApiApplication.class, args);
    }


    @Bean
    public ShutdownHook shutdownHook() {
        return new ShutdownHook();
    }

    static class ShutdownHook implements SmartLifecycle {
        @Override
        public void start() {
            log.info("Starting Job ...");
        }
        @Override
        public void stop() {
            log.info("Graceful shutdown.....");
            final int MAX_TIME_OUT = 10;
            int timeOut = 0;
            while(timeOut++ < MAX_TIME_OUT){
                log.info("Waiting for all job finish...");
                try {
                    Thread.currentThread().sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        @Override
        public boolean isRunning() {
            return true;
        }
    }
}
