package com.apress.prospring6.six.boot;


import com.apress.prospring6.six.boot.repo.SingerRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("testcontainers")
@Testcontainers
@SpringBootTest(classes = {Chapter6Application.class})
public class Chapter6ApplicationV2Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter6Application.class);

    @Autowired
    SingerRepo singerRepo;

    @Test
    @DisplayName("should return all singers")
    void testFindAllWithJdbcTemplate() {
        var singers = singerRepo.findAll().toList();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("find singer by name")
    @Sql({"classpath:testcontainers/stored-function.sql"})
    void testStoredFunction() {
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        assertEquals("Ben", firstName);
    }
}
