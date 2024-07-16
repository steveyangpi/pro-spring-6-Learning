package com.apress.prospring6.six.repo;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;

import java.sql.Types;

import static com.apress.prospring6.six.QueryConstants.INSERT_SINGER_ALBUM;

public class InsertSingerAlbum extends BatchSqlUpdate {

    private static final int BATCH_SIZE = 10;

    public InsertSingerAlbum(DataSource dataSource) {
        super(dataSource, INSERT_SINGER_ALBUM);
        declareParameter(new SqlParameter("singer_id", Types.VARCHAR));
        declareParameter(new SqlParameter("title", Types.VARCHAR));
        declareParameter(new SqlParameter("release_date", Types.DATE));
        setBatchSize(BATCH_SIZE);
    }
}
