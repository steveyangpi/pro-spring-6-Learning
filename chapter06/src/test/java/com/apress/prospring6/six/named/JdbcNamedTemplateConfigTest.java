package com.apress.prospring6.six.named;

import com.apress.prospring6.six.config.EmbeddedJdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcNamedTemplateConfigTest {

    @Test
    public void testSpringJdbc(){
        var ctx = new AnnotationConfigApplicationContext(TestDbCfg.class);
        var namedTemplate = ctx.getBean("namedTemplate",NamedParameterJdbcTemplate.class);
        assertNotNull(namedTemplate);

        var singerDao = ctx.getBean("singerDao",SingerDao.class);

        assertEquals("John Mayer", singerDao.findNameById(1L));
        ctx.close();
    }

    @Import(EmbeddedJdbcConfig.class)
    @Configuration
    public static class TestDbCfg{

        @Autowired
        DataSource dataSource;

        @Bean
        public NamedParameterJdbcTemplate namedTemplate(){
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public SingerDao singerDao(){
            var dao = new NamedTemplateDao();
            dao.setNamedTemplate(namedTemplate());
            return dao;
        }
    }
}
