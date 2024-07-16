package com.apress.prospring6.six.repo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {StoredFunctionV1Test.TestContainersConfig.class, SingerJdbcRepo.class})
public class StoredFunctionV1Test {

    @Autowired
    SingerRepo singerRepo;

    @Test
    void testFindAllQuery() {
        var singers = singerRepo.findAll();
        assertEquals(3, singers.size());
    }

    @Test
    @Sql({"classpath:testcontainers/stored-function.sql"})
    void testStoredFunction() {
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        assertEquals("Ben", firstName);
    }

    @Configuration
    public static class TestContainersConfig {
        private static final Logger LOGGER = LoggerFactory.getLogger(TestContainersConfig.class);

        MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:11.1.2");

        @PostConstruct
        void initialize() {
            mariaDB.start();
        }

        @PreDestroy
        void tearDown() {
            mariaDB.stop();
        }

        @Bean
        DataSource dataSource() {
            try {
                var dataSource = new BasicDataSource();
                dataSource.setDriverClassName(mariaDB.getDriverClassName());
                dataSource.setUrl(mariaDB.getJdbcUrl());
                dataSource.setUsername(mariaDB.getUsername());
                dataSource.setPassword(mariaDB.getPassword());
                return dataSource;
            } catch (Exception e) {
                LOGGER.info("MariaDB TestContainers DataSource bean cannot be created!", e);
                return null;
            }
        }
    }
}
