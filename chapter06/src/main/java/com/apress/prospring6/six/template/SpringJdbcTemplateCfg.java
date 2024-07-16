package com.apress.prospring6.six.template;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.apress.prospring6.six.QueryConstants.PARAMETRIZED_FIND_NAME;

interface SingerDao {
    String findNameById(Long id);
}

class JdbcSingerDao implements SingerDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return jdbcTemplate.queryForObject(PARAMETRIZED_FIND_NAME,String.class,id);
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringJdbcTemplateCfg {

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }
}
