package com.apress.prospring6.six.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class SimpleDataSourceCfg {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleDataSourceCfg.class);
    @Value("org.mariadb.jdbc.Driver")
    private String driverClassName;

    @Value("jdbc:mariadb://localhost:3306/musicdb?useSSL=false")
    private String url;

    @Value("prospring6")
    private String username;

    @Value("prospring6")
    private String password;

    @Bean
    public DataSource dataSource(){
        try {
            var dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }catch (Exception e){
            LOGGER.error("Simple DataSourcef bean cannot be created!",e);
            return null;
        }
    }
}
