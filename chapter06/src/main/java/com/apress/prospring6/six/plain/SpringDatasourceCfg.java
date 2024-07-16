package com.apress.prospring6.six.plain;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.apress.prospring6.six.QueryConstants.FIND_NAME;

interface SingerDao {
    String findNameById(Long id);
}

class JdbcSingerDao implements SingerDao, InitializingBean {
    private static Logger LOGGER = LoggerFactory.getLogger(JdbcSingerDao.class);
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set dataSource on SingerDao");
        }
    }

    @Override
    public String findNameById(Long id) {
        var result = "";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(FIND_NAME + id);
             var resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                return resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            }

        } catch (SQLException ex) {
            LOGGER.error("Problem when executing SELECT!", ex);
        }
        return result;
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringDatasourceCfg {

    @Autowired
    DataSource dataSource;

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setDataSource(dataSource);
        return dao;
    }
}
