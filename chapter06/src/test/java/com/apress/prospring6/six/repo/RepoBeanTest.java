package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.EmbeddedJdbcConfig;
import com.apress.prospring6.six.plain.records.Album;
import com.apress.prospring6.six.plain.records.Singer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepoBeanTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepoBeanTest.class);
    @Test
    public void testFindAllWithMappingSqlQuery(){
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class, SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo",SingerRepo.class);
        assertNotNull(singerRepo);

        var singers = singerRepo.findAll();
        assertEquals(3,singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        ctx.close();
    }

    @Test
    public void testFindByNameWithMappingSqlQuery(){
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class,SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo",SingerRepo.class);
        assertNotNull(singerRepo);

        var singers = singerRepo.findByFirstName("Ben");
        assertEquals(1,singers.size());
        LOGGER.info("Result: {}",singers.get(0));

        ctx.close();
    }

    @Test
    public void testUpdateWithSqlUpdate(){
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class,SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo",SingerRepo.class);
        assertNotNull(singerRepo);

        var singer = new Singer(1L,"John Clayton","Mayer",
                LocalDate.of(1977,10,16),
                Set.of());
        singerRepo.update(singer);

        var singers = singerRepo.findByFirstName("John Clayton");
        assertEquals(1,singers.size());
        LOGGER.info("Result: {}",singers.get(0));

        ctx.close();
    }

    @Test
    public void testInsertWithSqlUpdate(){
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class,SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo",SingerRepo.class);
        assertNotNull(singerRepo);

        var singer = new Singer(null,"Ed","Sheeran",
                LocalDate.of(1991,2,27),
                Set.of());
        singerRepo.insert(singer);

        var singers = singerRepo.findByFirstName("Ed");
        assertEquals(1,singers.size());
        LOGGER.info("Result: {}",singers.get(0));

        ctx.close();
    }

    @Test
    public void testInsertAlbumsWithBatchSqlUpdate(){
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class, SingerJdbcRepo.class);
        var singerRepo = ctx.getBean("singerRepo",SingerRepo.class);
        assertNotNull(singerRepo);

        var singer = new Singer(null,"BB","King",
                LocalDate.of(1940,9,16),
                new HashSet<>());

        var album = new Album(null,null,"My Kind of Blues",LocalDate.of(1961,8,18));
        singer.albums().add(album);

        album = new Album(null,null,"A Heart Full of Blues",
                LocalDate.of(1962,4,20));
        singer.albums().add(album);

        singerRepo.insertWithAlbum(singer);

        var singers = singerRepo.findAllWithAlbums();
        assertEquals(4,singers.size());
        singers.forEach(s -> LOGGER.info(s.toString()));

        ctx.close();
    }
}
