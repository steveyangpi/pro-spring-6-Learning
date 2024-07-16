package com.apress.prospring6.eight.service;

import com.apress.prospring6.eight.view.SingerSummaryRecord;
import com.apress.prospring6.eight.view.SingerSummary;

import java.util.stream.Stream;

public interface SingerSummaryService {

    String ALL_SINGER_JPQL_QUERY = """
            select new com.apress.prospring6.eight.view.SingerSummary(
            s.firstName, s.lastName, a.title) from Singer s 
            left join s.albums a 
            where a.releaseDate=(select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id)
            """;

    String ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY ="""
            select s.firstName, s.lastName, a.title from Singer s 
            left join s.albums a 
            where a.releaseDate=(select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id)
            """;

    Stream<SingerSummary> findAll();
    Stream<SingerSummaryRecord> findAllAsRecord();
}
