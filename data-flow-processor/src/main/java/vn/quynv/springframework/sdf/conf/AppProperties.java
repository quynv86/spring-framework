package vn.quynv.springframework.sdf.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app-conf")
public class AppProperties {
    private String messTemplate;
}
