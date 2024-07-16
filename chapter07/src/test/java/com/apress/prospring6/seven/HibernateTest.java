package com.apress.prospring6.seven;

import com.apress.prospring6.seven.base.config.HibernateConfig;
import com.apress.prospring6.seven.base.dao.SingerDao;
import com.apress.prospring6.seven.base.entities.Album;
import com.apress.prospring6.seven.base.entities.Instrument;
import com.apress.prospring6.seven.base.entities.Singer;
import jakarta.annotation.PostConstruct;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {HibernateTest.TestContainersConfig.class})
public class HibernateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTest.class);

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
    SingerDao singerDao;

    @Test
    @DisplayName("should return all singers;")
    void testFindAll() {
        var singers = singerDao.findAll();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("should return singer by id")
    void testFindById() {
        var singer = singerDao.findById(2L);
        assertEquals("Ben", singer.getFirstName());
        LOGGER.info(singer.toString());
    }

    @Test
    @DisplayName("should insert a singer with associations")
    @Sql(statements = {
            "delete from ALBUM where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER_INSTRUMENT where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER where FIRST_NAME = 'BB'"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testInsertSinger() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("My kind of Blues");
        album.setReleaseDate(LocalDate.of(1961, 7, 18));
        singer.addAlbum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);
        singerDao.save(singer);

        assertNotNull(singer.getId());

        var singers = singerDao.findAllWithAlbum();
        assertEquals(4, singers.size());
        listSingersWithAssociations(singers);
    }

    @Configuration
    @Import(HibernateConfig.class)
    public static class TestContainersConfig {
        @Autowired
        Properties hibernateProperties;

        @PostConstruct
        public void initialize() {
            hibernateProperties.put(Environment.FORMAT_SQL, true);
            hibernateProperties.put(Environment.USE_SQL_COMMENTS, true);
            hibernateProperties.put(Environment.SHOW_SQL, true);
        }
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
        var singer = singerDao.findById(5L);

        assertNotNull(singer);

        assertEquals("Simone", singer.getLastName());

        var album = singer.getAlbums().stream().filter(
                a -> a.getTitle().equals("I Put a Spell on You")).findFirst().orElse(null);
        assertNotNull(album);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();
        singerDao.save(singer);

        var nina = singerDao.findById(5L);
        assertEquals(version + 1, nina.getVersion());

        listSingersWithAssociations(singerDao.findAllWithAlbum());
    }

    @Test
    @Sql(scripts = {"classpath:testcontainers/add-chuck.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("should delete a singer")
    void testDelete() {
        Singer singer = singerDao.findById(6L);

        assertNotNull(singer);

        singerDao.delete(singer);
        listSingersWithAssociations(singerDao.findAllWithAlbum());
    }

    @Test
    void testNativeQuery() {
        var singer = singerDao.findAllDetails("Ben", "Barnes");
        assertAll("Native Singer Test",
                () -> assertEquals("Ben", singer.getFirstName()),
                () -> assertEquals("Barnes", singer.getLastName()),
                () -> assertEquals(1, singer.getAlbums().size()),
                () -> assertEquals(3, singer.getInstruments().size())
        );
    }

    @Test
    void testFindAllNamesByProjection(){
        var singers = singerDao.findAllNamesByProjection();
        assertEquals(3,singers.size());
        singers.forEach(LOGGER::info);
    }

    @Sql({"classpath:testcontainers/stored-function.sql"})
    @Test
    void testFindFirstNameById(){
        var res = singerDao.findFirstNameById(1L);
        assertEquals("John",res);
    }

    @Sql({"classpath:testcontainers/stored-procedure.sql"})
    @Test
    void testFindFirstNameByIdUsingProc(){
        var res = singerDao.findFirstNameByIdUsingProc(1L);
        assertEquals("John",res);
    }

    private static void listSingersWithAssociations(List<Singer> singers) {
        LOGGER.info(" ---- Listing singers with instruments:");
        for (Singer singer : singers) {
            LOGGER.info(singer.toString());
            if (singer.getAlbums() != null) {
                for (Album album : singer.getAlbums()) {
                    LOGGER.info("\t" + album.toString());
                }
            }
            if (singer.getInstruments() != null) {
                for (Instrument instrument : singer.getInstruments()) {
                    LOGGER.info("\tInstrument: " + instrument.getInstrumentId());
                }
            }
        }
    }
}
