package com.apress.prospring6.nine.repos;

import com.apress.prospring6.nine.entities.Singer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class SingerRepoImpl implements SingerRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Stream<Singer> findAll() {
        return em.createNamedQuery(Singer.FIND_ALL, Singer.class).getResultList().stream();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        var found = em.find(Singer.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    @Override
    public Optional<Singer> findByFirstNameAndLast(String fn, String ln) {
        var result = em.createNamedQuery(Singer.FIND_BY_FIRST_AND_LAST_NAME, Singer.class)
                .setParameter("fn", fn).setParameter("ln", ln).getSingleResult();
        return result == null ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Long countAllSingers() {
        return em.createNamedQuery(Singer.COUNT_ALL, Long.class).getSingleResult();
    }

    @Override
    public Singer save(Singer singer) {
        if (singer.getId() == null) {
            em.persist(singer);
            return singer;
        } else {
            return em.merge(singer);
        }
    }
}
