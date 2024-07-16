package com.apress.prospring6.six.plain.dao.pojos;

import com.apress.prospring6.six.plain.dao.CoreDao;
import com.apress.prospring6.six.plain.pojos.Singer;

import java.util.Set;

public interface SingerDao extends CoreDao {
    Set<Singer> findAll();

    Set<Singer> findByFirstName(String firstName);

    String findNameById(Long id);

    String findLastNameById(Long id);

    String findFirstNameById(Long id);

    Singer insert(Singer singer);

    void update(Singer singer);

    void delete(Long singerId);

    Set<String> findAllWithAlbums();

    void insertWithAlbum(Singer singer);
}
