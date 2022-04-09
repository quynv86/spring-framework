package vn.quynv.springframework.sdf.conf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app-conf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppProperties {
    private String messTemplate;
}
