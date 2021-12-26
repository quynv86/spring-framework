package vn.quynv.springframework.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class WebFluxR2DbcApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(WebFluxR2DbcApplication.class, args);
    }
}
