package com.apress.prospring6.six.hybird;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcTemplateConfigTest {

    @Disabled("needs MariaDB running,comment this to run")
    @Test
    public void testSpringJdbc(){
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerDao = ctx.getBean("singerDao",SingerDao.class);

        assertEquals("John Mayer",singerDao.findNameById(1L));
        ctx.close();
    }
}
