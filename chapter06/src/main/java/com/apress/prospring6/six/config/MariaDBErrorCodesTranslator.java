package com.apress.prospring6.six.config;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class MariaDBErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlex) {
        if(sqlex.getErrorCode() == -12345){
            return new DeadlockLoserDataAccessException(task,sqlex);
        }
        return null;
    }
}
