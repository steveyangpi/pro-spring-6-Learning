package com.apress.prospring6.six.hybird;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import com.apress.prospring6.six.config.MariaDBErrorCodesTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.apress.prospring6.six.QueryConstants.PARAMETRIZED_FIND_NAME;

interface SingerDao{
    String findNameById(Long id);
}

class JdbcSingerDao implements SingerDao, InitializingBean {
    private static Logger LOGGER = LoggerFactory.getLogger(JdbcSingerDao.class);
    private JdbcTemplate jdbcTemplate;

    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        var jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        var errorTranslator = new MariaDBErrorCodesTranslator();
        errorTranslator.setDataSource(dataSource);

        jdbcTemplate.setExceptionTranslator(errorTranslator);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(dataSource == null){
            throw new BeanCreationException("Must set dataSource on SingerDao");
        }
    }

    @Override
    public String findNameById(Long id) {
        return jdbcTemplate.queryForObject(PARAMETRIZED_FIND_NAME,String.class,id);
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringDatasourceCfg {

    @Autowired
    DataSource dataSource;

    @Bean
    public SingerDao singerDao() {
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setDataSource(dataSource);
        return dao;
    }
}
