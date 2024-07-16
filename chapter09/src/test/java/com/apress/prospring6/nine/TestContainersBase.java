package com.apress.prospring6.nine;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestContainersBase {

    @Container
    static MariaDBContainer <?> mariaDB = new MariaDBContainer<>("mariadb:11.1.2");

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName",mariaDB::getDriverClassName);
        registry.add("jdbc.url",mariaDB::getJdbcUrl);
        registry.add("jdbc.username",mariaDB::getUsername);
        registry.add("jdbc.password",mariaDB::getPassword);
    }
}
