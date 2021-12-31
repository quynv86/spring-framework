package vn.quynv.springframework.webflux.support;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.r2dbc.initialize-schema")
@Slf4j
@Data
public class DatabaseSupportProperties {
    private Boolean initialize = new Boolean(false);
    private String scriptPath;
}
