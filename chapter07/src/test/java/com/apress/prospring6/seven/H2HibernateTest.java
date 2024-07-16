package com.apress.prospring6.seven;

import com.apress.prospring6.seven.base.dao.SingerDao;
import com.apress.prospring6.seven.base.entities.Album;
import com.apress.prospring6.seven.base.entities.Singer;
import com.apress.prospring6.seven.config.HibernateTestConfig;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringJUnitConfig(classes = {HibernateTestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H2HibernateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2HibernateTest.class);

    @Autowired
    SingerDao singerDao;

    @Commit
    @Test
    @Order(1)
    @DisplayName("01. should insert a singers with albums")
    public void testInsert() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(LocalDate.of(1961, 7, 18));
        singer.addAlbum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);

        var created = singerDao.save(singer);
        assertNotNull(created.getId());
    }

    @Test
    @Order(2)
    @DisplayName("02. should return all singers")
    public void testFindAll() {
        var singers = singerDao.findAll();
        assertEquals(1, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @Order(3)
    @DisplayName("03. should update a singer")
    public void testUpdate() {
        var singer = singerDao.findAll().get(0);

        assertNotNull(singer);
        singer.setFirstName("Riley B. ");
        int version = singer.getVersion();
        singerDao.save(singer);
        var bb = singerDao.findById(singer.getId());

        assertEquals(version + 1, bb.getVersion());
    }

    @Test
    @Order(4)
    @DisplayName("04. should delete a singer")
    void testDelete() {
        Singer singer = singerDao.findAll().get(0);

        assertNotNull(singer);

        singerDao.delete(singer);

        assertEquals(0, singerDao.findAll().size());
    }

}
