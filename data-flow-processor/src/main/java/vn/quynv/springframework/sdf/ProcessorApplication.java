package vn.quynv.springframework.sdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import vn.quynv.springframework.sdf.conf.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcessorApplication.class);
    }
}
