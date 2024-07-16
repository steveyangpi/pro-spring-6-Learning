package com.apress.prospring6.seven.base.dao;

import com.apress.prospring6.seven.base.entities.Album;
import com.apress.prospring6.seven.base.entities.Instrument;
import com.apress.prospring6.seven.base.entities.Singer;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.Tuple;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Transactional
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDaoImpl.class);

    private SessionFactory sessionFactory;

    public SingerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Singer", Singer.class).list();
    }

    private static final String ALL_SELECT = """
            select distinct s.FIRST_NAME, s.LAST_NAME, a.TITLE, a.RELEASE_DATE, i.INSTRUMENT_ID
            from SINGER s
            inner join ALBUM a on s.id = a.SINGER_ID
            inner join SINGER_INSTRUMENT si on s.ID = si.SINGER_ID
            inner join INSTRUMENT i on si.INSTRUMENT_ID = i.INSTRUMENT_ID
            where s.FIRST_NAME = :firstName and s.LAST_NAME= :lastName
            """;

    @Transactional(readOnly = true)
    @Override
    public Singer findAllDetails(String firstName, String lastName) {
        List<Tuple> results = sessionFactory.getCurrentSession()
                .createNativeQuery(ALL_SELECT, Tuple.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .list();

        var singer = new Singer();

        for (Tuple item : results) {
            if (singer.getFirstName() == null && singer.getLastName() == null) {
                singer.setFirstName((String) item.get("FIRST_NAME"));
                singer.setLastName((String) item.get("LAST_NAME"));
            }
            var album = new Album();
            album.setTitle((String) item.get("TITLE"));
            album.setReleaseDate(((Date) item.get("RELEASE_DATE")).toLocalDate());
            singer.addAlbum(album);

            var instrument = new Instrument();
            instrument.setInstrumentId((String) item.get("INSTRUMENT_ID"));
            singer.getInstruments().add(instrument);
        }

        return singer;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAllWithAlbum() {
        return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum").list();
    }

    @Transactional(readOnly = true)

    @Override
    public Singer findById(Long id) {
        return sessionFactory.getCurrentSession().createNamedQuery("Singer.findById", Singer.class).setParameter("id", id).uniqueResult();
    }

    @Transactional
    @Override
    public Singer save(Singer singer) {
        var session = sessionFactory.getCurrentSession();
        if (singer.getId() == null) {
            session.persist(singer);
        } else {
            session.merge(singer);
        }
        LOGGER.info("Singer saved with id: " + singer.getId());
        return singer;
    }

    @Transactional
    @Override
    public void delete(Singer singer) {
        sessionFactory.getCurrentSession().remove(singer);
        LOGGER.info("Singer deleted with id: " + singer.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<String> findAllNamesByProjection() {
        List<Tuple> projResult = sessionFactory.getCurrentSession()
                .createQuery("select s.firstName as fn,s.lastName as ln from Singer s", Tuple.class)
                .getResultList();
        return projResult.stream().map(tuple -> tuple.get("fn", String.class) + " " + tuple.get("ln", String.class))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public String findFirstNameById(Long id) {
        final AtomicReference<String> firstNameResult = new AtomicReference<>();
        sessionFactory.getCurrentSession().doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{ ? = call getfirstnamebyid(?)}")) {
                function.registerOutParameter(1, Types.VARCHAR);
                function.setLong(2, id);
                function.execute();
                firstNameResult.set(function.getString(1));
            }
        });
        return firstNameResult.get();
    }

    @Transactional(readOnly = true)
    @Override
    public String findFirstNameByIdUsingProc(Long id) {
        ProcedureCall procedureCall =
                sessionFactory.getCurrentSession()
                        .createNamedStoredProcedureQuery("getFirstNameByIdProc");
        procedureCall.setParameter("in_id", id);
        procedureCall.setParameter("fn_res", "");
        procedureCall.execute();
        return procedureCall.getOutputParameterValue("fn_res").toString();

        //does not need the named query declaration
       /* StoredProcedureQuery query  sessionFactory.getCurrentSession()
                .createStoredProcedureQuery("getFirstNameByIdProc");
        query.registerStoredProcedureParameter("in_id",Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("fn_res",String.class,ParameterMode.OUT);

        query.setParameter("in_id",1L);
        return (String) query.getOutputParameterValue("fn_res");*/
    }
}
