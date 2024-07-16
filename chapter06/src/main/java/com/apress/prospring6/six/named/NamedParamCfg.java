package com.apress.prospring6.six.named;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

import static com.apress.prospring6.six.QueryConstants.NAMED_FIND_NAME;

interface SingerDao{
    String findNameById(Long id);
}

class NamedTemplateDao implements SingerDao{

    private NamedParameterJdbcTemplate namedTemplate;

    public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return namedTemplate.queryForObject(NAMED_FIND_NAME, Map.of("singerId",id),String.class);
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class NamedParamCfg {

    @Autowired
    DataSource dataSource;

    @Bean
    public NamedParameterJdbcTemplate namedTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public SingerDao singerDao(){
        var dao = new NamedTemplateDao();
        dao.setNamedTemplate(namedTemplate());
        return dao;
    }
}
