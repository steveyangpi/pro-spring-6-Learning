package com.apress.prospring6.nine.boot;

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
@ComponentScan(basePackages = {"com.apress.prospring6.nine.boot.repos", "com.apress.prospring6.nine.bool.services"})
@EnableTransactionManagement
public class TransactionConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    @ConfigurationProperties("spring.jpa.properties")
    public Properties jpaProperties() {
        return new Properties();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.apress.prospring6.nine.boot.entities");
        sessionFactoryBean.setHibernateProperties(jpaProperties());
        return sessionFactoryBean;
    }
}
