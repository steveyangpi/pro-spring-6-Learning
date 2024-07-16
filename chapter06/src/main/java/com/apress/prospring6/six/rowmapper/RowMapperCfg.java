package com.apress.prospring6.six.rowmapper;


import com.apress.prospring6.six.config.BasicDataSourceCfg;
import com.apress.prospring6.six.plain.records.Album;
import com.apress.prospring6.six.plain.records.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.apress.prospring6.six.QueryConstants.ALL_JOIN_SELECT;
import static com.apress.prospring6.six.QueryConstants.ALL_SELECT;

interface SingerDao {
    Set<Singer> findAll();

    Set<Singer> findAllWithAlbums();
}

class RowMapperDao implements SingerDao {

    private NamedParameterJdbcTemplate namedTemplate;

    public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    @Override
    public Set<Singer> findAll() {
        return new HashSet<>(namedTemplate.query(ALL_SELECT, (rs, rowNum) ->
                new Singer(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        Set.of())
        ));
    }

    /*static class SingerMapper implements RowMapper<Singer> {
        @Override
        public Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Singer(rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("birth_date").toLocalDate(),
                    Set.of());
        }
    }*/

   /* @Override
    public Set<Singer> findAllWithAlbums() {
        return new HashSet<>(namedTemplate.query(ALL_JOIN_SELECT, new SingerWithAlbumsExtractor()));
    }

    static class SingerWithAlbumsExtractor implements ResultSetExtractor<Set<Singer>> {
        @Override
        public Set<Singer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;
            while (rs.next()) {
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null) {
                    singer = new Singer(id,
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate(),
                            new HashSet<>());
                    map.put(id, singer);
                }

                var albumId = rs.getLong("album_id");
                if (albumId > 0) {
                    Album album = new Album(albumId, id, rs.getString("title"),
                            rs.getDate("release_date").toLocalDate()
                    );
                    singer.albums().add(album);
                }
            }
            return new HashSet<>(map.values());
        }
    }*/

    @Override
    public Set<Singer> findAllWithAlbums() {
        return namedTemplate.query(ALL_JOIN_SELECT, rs -> {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;
            while (rs.next()) {
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null) {
                    singer = new Singer(id, rs.getString("first_name"), rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate(),
                            new HashSet<>());
                    map.put(id, singer);
                }

                var albumId = rs.getLong("album_id");
                if (albumId > 0) {
                    Album album = new Album(albumId, id, rs.getString("title"),
                            rs.getDate("release_date").toLocalDate());
                    singer.albums().add(album);
                }
            }
            return new HashSet<>(map.values());
        });
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class RowMapperCfg {

    @Autowired
    DataSource dataSource;

    @Bean
    public NamedParameterJdbcTemplate namedTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public SingerDao singerDao() {
        var dao = new RowMapperDao();
        dao.setNamedTemplate(namedTemplate());
        return dao;
    }
}
