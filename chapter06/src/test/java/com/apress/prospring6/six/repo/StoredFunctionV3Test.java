package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.io.Resources;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringJUnitConfig(classes = {StoredFunctionV3Test.TestContainersConfig.class, SingerJdbcRepo.class})
public class StoredFunctionV3Test {

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
    void testStoredFunction() {
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        assertEquals("Ben", firstName);
    }

    @Configuration
    @Import(BasicDataSourceCfg.class)
    public static class TestContainersConfig {

        @PostConstruct
        public void initialize() throws ScriptException, IOException {
            final String script1 = Resources.toString(Resources.getResource("testcontainers/create-schema.sql"), StandardCharsets.UTF_8);
            final String script2 = Resources.toString(Resources.getResource("testcontainers/original-stored-function.sql"), StandardCharsets.UTF_8);
            mariaDB.start();
            ScriptUtils.executeDatabaseScript(new JdbcDatabaseDelegate(mariaDB, ""), "schema.sql", script1, false, false, ScriptUtils.DEFAULT_COMMENT_PREFIX,
                    ScriptUtils.DEFAULT_STATEMENT_SEPARATOR, "$$", "$$$");
            ScriptUtils.executeDatabaseScript(new JdbcDatabaseDelegate(mariaDB, ""), "schema.sql", script2, false, false, ScriptUtils.DEFAULT_COMMENT_PREFIX,
                    ScriptUtils.DEFAULT_STATEMENT_SEPARATOR, "$$", "$$$");
        }
    }
}
