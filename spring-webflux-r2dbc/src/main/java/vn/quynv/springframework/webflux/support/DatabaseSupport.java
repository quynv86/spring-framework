package vn.quynv.springframework.webflux.support;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Qualifier("r2dbDatabaseSupport")
@Slf4j
public class DatabaseSupport {

    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private DatabaseSupportProperties properties;

    public void initSchema() {
        if(properties==null) {
            log.info("Initialize Schema Properties Not Set. Ignored");
            return;
        }
        if(!properties.getInitialize()) {
            log.info("Initialize Schema Properties Set To FALSE. Ignored");
            return;
        }
        log.info("Initialize - Schema - Scripts : {}", properties.getScriptPath());
        log.info("Initializing Database Schema .............");
        long startInit = System.currentTimeMillis();
        try {

            String SCRIPTS = new String(Files.readAllBytes(Paths.get(properties.getScriptPath())));
            log.info("Initialize Script : \n{}", SCRIPTS);
            Flux.using(() -> Flux.from(factory.create()).blockLast()
                    , connection -> {
                        return Flux.from(connection.createStatement(SCRIPTS).execute())
                                .flatMap(result -> result.getRowsUpdated());
                    }
                    , connection -> {
                        Flux.from(connection.close()).blockLast();
                    }
            ).blockLast();
            log.info("Initializing Database Schema Done. Took {} (ms)", System.currentTimeMillis() - startInit);
        } catch (IOException e) {
            log.warn("Can not read scripts file: {}", e.getMessage());
            throw new RuntimeException("Can not read scripts file.");
        }
    }
}
