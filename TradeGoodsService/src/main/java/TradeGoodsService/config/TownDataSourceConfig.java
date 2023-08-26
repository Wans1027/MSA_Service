package TradeGoodsService.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "monitorEntityManagerFactory"
        , transactionManagerRef = "monitorTransactionManager"
        , basePackages = "TradeGoodsService.repository.town"
)
public class TownDataSourceConfig {
    @Autowired
    private Environment env;

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;


    @Bean
    public DataSource monitorDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.town.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.town.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.town.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.town.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean monitorEntityManagerFactory (EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder
                .dataSource(monitorDataSource())
                .packages("TradeGoodsService.entity.town")
                .persistenceUnit("monitorEntityManager")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager monitorTransactionManager(@Qualifier(value = "monitorEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
