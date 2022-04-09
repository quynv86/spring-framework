package vn.quynv.springframework.sdf.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.conf")
public class AppProperties {
    private String messTemplate;

    public String getMessTemplate() {
        return messTemplate;
    }

    public void setMessTemplate(String messTemplate) {
        this.messTemplate = messTemplate;
    }
}
