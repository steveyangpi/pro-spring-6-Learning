package com.apress.prospring6.six.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import javax.sql.DataSource;

@Configuration
public class JndiDataSourceCfg {
    private static Logger LOGGER = LoggerFactory.getLogger(JndiDataSourceCfg.class);

    @Bean
    public DataSource dataSource() {
        try {
            return (DataSource) new JndiTemplate().lookup("java:comp/env/jdbc/musicdb");
        }catch (Exception e){
            LOGGER.error("JNDI DataSource bean cannot be created!",e);
            return null;
        }
    }
}
