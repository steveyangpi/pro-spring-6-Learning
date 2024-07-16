package com.apress.prospring6.six.repo;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

import static com.apress.prospring6.six.QueryConstants.UPDATE_SINGER;

public class UpdateSinger extends SqlUpdate {

    public UpdateSinger(DataSource dataSource) {
        super(dataSource, UPDATE_SINGER);
        super.declareParameter(new SqlParameter("first_name", Types.VARBINARY));
        super.declareParameter(new SqlParameter("last_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("birth_date", Types.DATE));
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }
}
