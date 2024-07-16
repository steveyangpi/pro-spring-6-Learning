package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.plain.records.Singer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static com.apress.prospring6.six.QueryConstants.ALL_SELECT;

public class SelectAllSingers extends MappingSqlQuery<Singer> {

    public SelectAllSingers(DataSource dataSource) {
        super(dataSource, ALL_SELECT);
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Singer(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date").toLocalDate(),
                Set.of());
    }
}
