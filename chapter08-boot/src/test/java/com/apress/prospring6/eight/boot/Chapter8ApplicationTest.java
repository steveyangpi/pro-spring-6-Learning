package com.apress.prospring6.eight.boot;

import com.apress.prospring6.eight.boot.service.SingerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringBootTest(classes = Chapter8Application.class)
public class Chapter8ApplicationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter8ApplicationTest.class);

    @Autowired
    SingerService singerService;

    @Test
    @DisplayName("should return all singers")
    void testFindAllWithJdbcTemplate() {
        var singers = singerService.findAll().peek(singer -> LOGGER.info(singer.toString())).toList();
        assertEquals(3, singers.size());
    }

    @Test
    @DisplayName("should return all singers with albums")
    void testFindAllWithAlbum(){
        var singers = singerService.findAllWithAlbum().peek(
                s -> {
                    LOGGER.info(s.toString());
                    if(s.getAlbums() != null){
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum:" + a.toString()));
                    }
                    if(s.getInstruments() !=null){
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument:" + i.toString()));
                    }
                }).toList();

        assertEquals(3, singers.size());
    }
}
