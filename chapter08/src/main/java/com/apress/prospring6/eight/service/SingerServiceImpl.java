package com.apress.prospring6.eight.service;

import com.apress.prospring6.eight.entities.Singer;
import jakarta.persistence.*;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service("jpaSingerService")
@Repository
@Transactional
public class SingerServiceImpl implements SingerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public Stream<Singer> findAllWithAlbum() {
        return em.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class)
                .getResultList().stream();
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<Singer> findAll() {
        return em.createNamedQuery(Singer.FIND_ALL, Singer.class).getResultList().stream();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Singer> findById(Long id) {
        TypedQuery<Singer> query = em.createNamedQuery(Singer.FIND_SINGER_BY_ID, Singer.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Singer singer) {
        if (singer.getId() == null) {
            LOGGER.info("Inserting new singer");
            em.persist(singer);
        } else {
            em.merge(singer);
            LOGGER.info("Updating existing singer");
        }
        LOGGER.info("Singer saved with id: " + singer.getId());
    }

    @Override
    public void delete(Singer singer) {
        var mergedContact = em.merge(singer);
        em.remove(mergedContact);

        LOGGER.info("Singer with id: " + singer.getId() + "deleted successfully");
    }

  /*  @SuppressWarnings({"unchecked"})
    @Override
    public Stream<Singer> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, Singer.class).getResultList().stream();
    }*/

    @SuppressWarnings({"unchecked"})
    @Override
    public Stream<Singer> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, "singerResult").getResultList().stream();
    }

    @Override
    public String findFirstNameById(Long id) {
       return em.createNamedQuery("Singer.getFirstNameById(?)")
               .setParameter(1,id).getSingleResult().toString();
    }

    @Override
    public String findFirstNameByIdUsingProc(Long id) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("getFirstNameByIdProc");
        query.setParameter("in_id",1L);

        query.execute();
        return (String) query.getOutputParameterValue("fn_res");
    }
}
