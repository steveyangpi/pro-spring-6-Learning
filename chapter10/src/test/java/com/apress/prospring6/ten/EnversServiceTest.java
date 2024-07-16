package com.apress.prospring6.ten;

import com.apress.prospring6.ten.config.AuditCfg;
import com.apress.prospring6.ten.config.EnversConfig;
import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.service.SingerAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@Sql({"classpath:testcontainers/audit/drop-schema.sql", "classpath:testcontainers/audit/create-schema.sql"})
@SpringJUnitConfig(classes = {EnversConfig.class, AuditCfg.class})
public class EnversServiceTest extends TestContainersBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnversServiceTest.class);

    @Autowired
    SingerAuditService auditService;

    @BeforeEach
    void setUp() {
        var singer = new SingerAudit();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));
        auditService.save(singer);
    }

    @Test
    void testFindAuditByRevision() {
        var singer = auditService.findAll().findFirst().orElse(null);
        assertNotNull(singer);
        singer.setFirstName("Riley B.");
        auditService.save(singer);

        var oldSinger = auditService.findAuditByRevision(singer.getId(), 1);
        assertEquals("BB", oldSinger.getFirstName());
        LOGGER.info(">> old singer: {}", oldSinger);

        var newSinger = auditService.findAuditByRevision(singer.getId(), 2);
        assertEquals("Riley B.", newSinger.getFirstName());
        LOGGER.info(">> update singer: {} ", newSinger);
    }

    @Test
    void testFindAuditAfterDeletion(){

        var singer = auditService.findAll().findFirst().orElse(null);
        auditService.delete(singer.getId());

        var deletedSinger = auditService.findAuditByRevision(singer.getId(),1);
        assertEquals("BB",deletedSinger.getFirstName());
        LOGGER.info(">> deleted singer: {}", deletedSinger);
    }
}
