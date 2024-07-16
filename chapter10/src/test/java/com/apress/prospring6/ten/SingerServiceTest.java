package com.apress.prospring6.ten;

import com.apress.prospring6.ten.config.DataJpaCfg;
import com.apress.prospring6.ten.service.SingerService;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {SingerServiceTest.TestContainersConfig.class})
public class SingerServiceTest extends TestContainersBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingerServiceTest.class);

    @Autowired
    SingerService singerService;

    @Test
    public void testFindAll() {
        var singers = singerService.findAll().peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(3, singers.size());
    }

    @Test
    public void testFindByFirstName() {
        var singers = singerService.findByFirstName("John").peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(2, singers.size());
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        var singers = singerService.findByFirstNameAndLastName("John", "Mayer")
                .peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(1, singers.size());
    }

    @Test
    public void testFindByLastName() {
        var singers = singerService.findByLastName("Mayer")
                .peek(s -> LOGGER.info(s.getFullName())).toList();
        assertEquals(1, singers.size());
    }

    @Rollback
    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    @DisplayName("should update a singer's name")
    public void testUpdateFirstNameByQuery() {
        var nina = singerService.updateFirstName("Eunice Kathleen", 5L);
        assertAll("nina was not updated",
                () -> assertNotNull(nina),
                () -> assertEquals("Eunice Kathleen", nina.getFirstName()));
    }

    @Configuration
    @Import(DataJpaCfg.class)
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
            jpaProperties.put(Environment.STATEMENT_BATCH_SIZE, 30);
        }
    }
}
