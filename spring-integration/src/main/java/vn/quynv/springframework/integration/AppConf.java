package vn.quynv.springframework.integration;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath*:app-integration.xml")
public class AppConf {
}
