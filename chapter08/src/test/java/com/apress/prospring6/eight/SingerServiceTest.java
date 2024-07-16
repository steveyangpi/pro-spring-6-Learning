package com.apress.prospring6.eight;

import com.apress.prospring6.eight.config.JpaConfig;
import com.apress.prospring6.eight.entities.Album;
import com.apress.prospring6.eight.entities.Singer;
import com.apress.prospring6.eight.service.SingerService;
import com.apress.prospring6.eight.service.SingerSummaryService;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {SingerServiceTest.TestContainersConfig.class})
public class SingerServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerServiceTest.class);

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
    @Qualifier("jpaSingerService")
    SingerService singerService;

    @Autowired
    SingerSummaryService singerSummaryService;


    @Test
    @DisplayName("should return all singers")
    void testFindAll() {
        var singers = singerService.findAll().toList();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("should return all singers with albums")
    void testFindAllWithAlbums() {
        var singers = singerService.findAllWithAlbum().peek(
                s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum:" + a.toString()));
                    }
                    if (s.getInstruments() != null) {
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument:" + i.getInstrumentId()));
                    }
                }).toList();
        assertEquals(3, singers.size());
    }

    @Test
    @DisplayName("should return all singers and their most recent albums as records")
    void testFindAllWithAlbumAsRecords() {
        var singers =
                singerSummaryService.findAllAsRecord()
                        .peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(2, singers.size());
    }

    @Test
    @DisplayName("should return all singers and their most recent albums as POJOs")
    void testFindAllAsPojos() {
        var singers =
                singerSummaryService.findAll()
                        .peek(s -> LOGGER.info(s.toString())).toList();
        assertEquals(2, singers.size());
    }

    @Test
    @DisplayName("should insert a singer with associations")
    @Sql(statements = {
            "delete from ALBUM where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER_INSTRUMENT where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER where FIRST_NAME = 'BB'"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testInsert() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);
        singerService.save(singer);

        assertNotNull(singer.getId());

        var singers = singerService.findAllWithAlbum().peek(
                s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum:" + a.toString()));
                    }
                    if (s.getInstruments() != null) {
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument:" + i.getInstrumentId()));
                    }
                }).toList();
        assertEquals(4, singers.size());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = {"classpath:testcontainers/remove-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @DisplayName("should update a singer")
    void testUpdate() {
        var singer = singerService.findById(5L).orElse(null);

        assertNotNull(singer);

        assertEquals("Simone", singer.getLastName());

        var album = singer.getAlbums().stream().filter(
                a -> a.getTitle().equals("I Put a Spell on You")).findFirst().orElse(null);
        Assertions.assertNotNull(album);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();

        singerService.save(singer);

        var nina = singerService.findById(5L).orElse(null);
        assertAll("nina was updated",
                () -> Assertions.assertNotNull(nina),
                () -> Assertions.assertEquals(version + 1, nina.getVersion()));
    }

    @Test
    public void testUpdateAllAlbumSet() {
        var singer = singerService.findById(1L).orElse(null);

        assertNotNull(singer);

        assertEquals("Mayer", singer.getLastName());

        var album = singer.getAlbums().stream().filter(a -> a.getTitle().equals("Battle Studies")).findAny().orElse(null);

        singer.setFirstName("John Clayton");
        singer.removeAlbum(album);
        singerService.save(singer);

        var singers = singerService.findAllWithAlbum().peek(
                s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum:" + a.toString()));
                    }
                    if (s.getInstruments() != null) {
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument:" + i.getInstrumentId()));
                    }
                }).toList();
        assertEquals(3, singers.size());
    }

    @Test
    @Sql(scripts = {"classpath:testcontainers/add-chuck.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("should delete a singer")
    public void testDelete() {
        var singer = singerService.findById(6L).orElse(null);

        assertNotNull(singer);
        singerService.delete(singer);

        var deleted = singerService.findById(6L);
        assertTrue(deleted.isEmpty());
    }

    @Sql({"classpath:testcontainers/stored-function.sql"})
    @Test
    void testFindFirstNameById(){
        var res = singerService.findFirstNameById(1L);
        assertEquals("John",res);
    }

    @Configuration
    @Import(JpaConfig.class)
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
        }
    }
}
