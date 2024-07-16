package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.repos.SingerAuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service("singerAuditService")
@Transactional
public class SingerAuditServiceImpl implements SingerAuditService {
    private final SingerAuditRepository singerAuditRepository;

    public SingerAuditServiceImpl(SingerAuditRepository singerAuditRepository) {
        this.singerAuditRepository = singerAuditRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<SingerAudit> findAll() {
        return StreamSupport.stream(singerAuditRepository.findAll().spliterator(),false);
    }

    @Transactional(readOnly = true)
    @Override
    public SingerAudit findById(Long id) {
       return singerAuditRepository.findById(id).orElse(null);
    }

    @Override
    public SingerAudit save(SingerAudit singer) {
        return singerAuditRepository.saveAndFlush(singer);
    }

    @Override
    public void delete(Long id) {
        singerAuditRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SingerAudit findAuditByRevision(Long id, int revision) {
        //var auditReader = AuditReaderFactory.get(entityManager);
        //return auditReader.find(SingerAudit.class,id,revision);
        return singerAuditRepository.findAuditByIdAndRevision(id,revision).orElse(null);
    }
}
