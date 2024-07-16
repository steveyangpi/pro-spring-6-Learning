package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({ "classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql" }) // This works
@SpringJUnitConfig(classes = {BasicDataSourceCfg.class, SingerJdbcRepo.class})
public class StoredFunctionV2Test {

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:11.1.2");

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariaDB::getDriverClassName);
        registry.add("jdbc.url", mariaDB::getJdbcUrl);
        registry.add("jdbc.username", mariaDB::getUsername);
        registry.add("jdbc.password", mariaDB::getPassword);
    }

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
}
