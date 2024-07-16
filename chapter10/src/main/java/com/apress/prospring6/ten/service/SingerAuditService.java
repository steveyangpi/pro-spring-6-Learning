package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.SingerAudit;

import java.util.stream.Stream;

public interface SingerAuditService {
    Stream<SingerAudit> findAll();
    SingerAudit findById(Long id);
    SingerAudit save(SingerAudit singerAudit);

    SingerAudit findAuditByRevision(Long id,int revision);
    void delete(Long id);
}
