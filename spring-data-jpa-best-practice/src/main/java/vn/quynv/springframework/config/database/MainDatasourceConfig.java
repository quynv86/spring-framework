package vn.quynv.springframework.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        value = "app.datasource.main.enable"
        ,havingValue = "true"
        ,matchIfMissing = true
)
@EnableJpaRepositories(
        entityManagerFactoryRef ="mainEntityManagerFactory"
        ,transactionManagerRef = "mainTransactionManager"
        ,basePackages = {"vn.quynv.springframework.repository"}
)
@EnableTransactionManagement
public class MainDatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.main")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource mainDataSource() {
        return mainDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }

    @Bean(name="mainEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("mainDataSource") DataSource dataSource) {
        Map<String,Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto","update");
        return builder.dataSource(dataSource).packages("vn.quynv.springframework.entity")
                .persistenceUnit("main")
                .properties(jpaProperties)
                .build();
    }

    @Bean(name="mainTransactionManager")
    @Primary
    public PlatformTransactionManager mainTransactionManager(@Qualifier("mainEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
