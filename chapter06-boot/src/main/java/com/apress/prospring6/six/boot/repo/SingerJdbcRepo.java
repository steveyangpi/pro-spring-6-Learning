package com.apress.prospring6.six.boot.repo;

import com.apress.prospring6.six.boot.records.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository("singerRepo")
public class SingerJdbcRepo implements SingerRepo {
    public static final String ALL_SELECT = "select * from SINGER";
    private JdbcTemplate jdbcTemplate;
    private StoredFunctionFirstNameById storedFunctionFirstNameById;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        storedFunctionFirstNameById = new StoredFunctionFirstNameById(jdbcTemplate.getDataSource());
    }

    @Override
    public Stream<Singer> findAll() {
        return jdbcTemplate.queryForStream(ALL_SELECT, (rs, rowNum) ->
                new Singer(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date"),
                        List.of()));
    }

    @Override
    public Optional<String> findFirstNameById(Long id) {
        var result = storedFunctionFirstNameById.execute(id).get(0);
        return result != null ? Optional.of(result) : Optional.empty();
    }

    static class StoredFunctionFirstNameById extends SqlFunction<String> {
        private static final String SQL_CALL = "select getfirstnamebyid(?)";

        public StoredFunctionFirstNameById(DataSource dataSource) {
            super(dataSource, SQL_CALL);
            declareParameter(new SqlParameter(Types.INTEGER));
            compile();
        }
    }
}
