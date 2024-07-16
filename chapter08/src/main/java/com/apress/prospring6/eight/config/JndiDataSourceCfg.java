package com.apress.prospring6.eight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
public class JndiDataSourceCfg {

    @Bean
    public DataSource dataSource(){
        var dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource("persistence/prospring6PersistenceUnit");
    }
}
