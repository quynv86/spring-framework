package vn.quynv.springframework;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigServer
public class AppConfigServer
{

    public static void main( String[] args )
    {
        SpringApplication.run(AppConfigServer.class, args);
    }

}
