package vn.quynv.springframework.webflux.support;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ConnectionFactory factory;

    @Autowired
    @Qualifier("r2dbDatabaseSupport")
    DatabaseSupport databaseSupport;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Context Started. Try to initialize database schema");
        if(databaseSupport==null) {
            log.warn("Database Schema Initialize Not Found.");
            return;
        }
        databaseSupport.initSchema();

    }
}
