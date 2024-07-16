package com.apress.prospring6.eight.boot.service;

import com.apress.prospring6.eight.boot.view.SingerSummary;
import com.apress.prospring6.eight.boot.view.SingerSummaryRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service("singerSummaryService")
@Repository
@Transactional(readOnly = true)
public class SingerSummaryServiceImpl implements SingerSummaryService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Stream<SingerSummary> findAll() {
        return em.createQuery(ALL_SINTER_SUMMARY_JPQL_QUERY,SingerSummary.class).getResultList().stream();
    }

    @Override
    public Stream<SingerSummaryRecord> findAllAsRecord() {
        return em.createQuery(ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY, Tuple.class).getResultList().stream()
                .map(tuple ->
                        new SingerSummaryRecord(
                                tuple.get(0,String.class),
                                tuple.get(1,String.class),
                                tuple.get(2,String.class))
                );
    }
}
