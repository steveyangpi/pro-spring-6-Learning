package com.apress.prospring6.eight.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.eight.boot.service"})
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    @ConfigurationProperties("spring.jpa.properties")
    public Properties hibernateProperties() {
        return new Properties();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory =new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.apress.prospring6.eight.boot.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
}
