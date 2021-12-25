package vn.quynv.springframework.cloud.integration.httpinboud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MovieWebProperties.class)
public class IntegrationHttpInboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegrationHttpInboundApplication.class,args);
    }
}
