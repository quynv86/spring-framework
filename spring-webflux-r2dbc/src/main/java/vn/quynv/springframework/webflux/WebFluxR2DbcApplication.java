package vn.quynv.springframework.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.quynv.springframework.webflux.support.DatabaseSupportProperties;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableConfigurationProperties({DatabaseSupportProperties.class})
@Slf4j
@EnableScheduling
public class WebFluxR2DbcApplication
{
    public static void main( String[] args )
    {

        SpringApplication application = new SpringApplication(WebFluxR2DbcApplication.class);
//        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationReadyEvent event) {
//                log.info("Lam gi thi lam");
//            }
//        });
        application.run(args);
    }

}
