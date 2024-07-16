package com.apress.prospring6.nine;

import com.apress.prospring6.nine.boot.Chapter9Application;
import com.apress.prospring6.nine.boot.entities.Album;
import com.apress.prospring6.nine.boot.services.AllService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringBootTest(classes = {Chapter9Application.class})
public class Chapter9ApplicationTest {

    @Autowired
    AllService service;

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = {"classpath:testcontainers/remove-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @DisplayName("should perform a rollback because DataIntegrityViolationException")
    void testRollbackRuntimeUpdate() {
        var singer = service.findByIdWithAlbums(5L).orElse(null);
        assertNotNull(singer);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");

        var album = new Album();
        album.setTitle("Little Girl Blue");
        album.setReleaseDate(LocalDate.of(1959, 2, 20));
        album.setSinger(singer);
        var albums = Set.of(album);

        assertThrows(DataIntegrityViolationException.class,
                () -> service.saveSingerWithAlbums(singer, albums),
                "PersistenceException not thrown");

        var nina = service.findByIdWithAlbums(5L).orElse(null);
        assertAll("nina was not updated",
                () -> assertNotNull(nina),
                () -> assertNotEquals("Eunice Kathleen", nina.getFirstName()),
                () -> assertNotEquals("Waymon", nina.getLastName()));
    }
}
