package com.apress.prospring6.ten.repos.envers;

import com.apress.prospring6.ten.entities.SingerAudit;

import java.util.Optional;

public interface CustomSingerAuditRepository {

    Optional<SingerAudit> findAuditByIdAndRevision(Long id, int revision);
}
