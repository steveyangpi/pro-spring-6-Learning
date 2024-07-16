package com.apress.prospring6.six.rowmapper;

import com.apress.prospring6.six.config.EmbeddedJdbcConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcNamedTemplateConfigTest {
    private static Logger LOGGER = LoggerFactory.getLogger(JdbcNamedTemplateConfigTest.class);

    @Test
    public void testFindAll(){
        var ctx = new AnnotationConfigApplicationContext(TestDbCfg.class);

        var singerDao = ctx.getBean("singerDao",SingerDao.class);

        var singers = singerDao.findAll();
        assertEquals(3,singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        ctx.close();
    }

    @Test
    public void testFindAllWithAlbums(){
        var ctx = new AnnotationConfigApplicationContext(TestDbCfg.class);

        var singerDao = ctx.getBean("singerDao",SingerDao.class);

        var singers = singerDao.findAllWithAlbums();
        assertEquals(3,singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        ctx.close();
    }

    @Import(EmbeddedJdbcConfig.class)
    @Configuration
    public static class TestDbCfg {

        @Autowired
        DataSource dataSource;

        @Bean
        public NamedParameterJdbcTemplate namedTemplate() {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public SingerDao singerDao() {
            var dao = new RowMapperDao();
            dao.setNamedTemplate(namedTemplate());
            return dao;
        }
    }
}