package com.apress.prospring6.six.repo;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;

import java.sql.Types;

import static com.apress.prospring6.six.QueryConstants.INSERT_SINGER;

public class InsertSinger extends SqlUpdate {

    public InsertSinger(DataSource dataSource){
        super(dataSource,INSERT_SINGER);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("last_name",Types.VARCHAR));
        super.declareParameter(new SqlParameter("birth_date",Types.DATE));
        super.setGeneratedKeysColumnNames("id");
        super.setReturnGeneratedKeys(true);
    }
}
