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
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "tradeEntityManagerFactory"
        , transactionManagerRef = "tradeTransactionManager"
        , basePackages = "TradeGoodsService.repository.trade"
)
public class TradeDataSourceConfig {
    @Autowired
    private Environment env;

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean
    public DataSource tradeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.trade.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.trade.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.trade.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.trade.password"));
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean tradeEntityManagerFactory (EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder
                .dataSource(tradeDataSource())
                .packages("TradeGoodsService.entity.trade")
                .persistenceUnit("tradeEntityManager")
                .properties(properties)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager tradeTransactionManager(@Qualifier(value = "tradeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}