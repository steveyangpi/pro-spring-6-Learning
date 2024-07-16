package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RepoDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepoDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BasicDataSourceCfg.class, SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);

        LOGGER.info("------------------------");
        var singers = singerRepo.findAll();
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        LOGGER.info("--------------------------");
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        LOGGER.info("Retrieved {} ", firstName);
        ctx.close();
    }
}
