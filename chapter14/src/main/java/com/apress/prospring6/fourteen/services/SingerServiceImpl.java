package com.apress.prospring6.fourteen.services;

import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.problem.InvalidCriteriaException;
import com.apress.prospring6.fourteen.problem.NotFoundException;
import com.apress.prospring6.fourteen.repos.SingerRepo;
import com.apress.prospring6.fourteen.util.CriteriaDto;
import com.apress.prospring6.fourteen.util.FieldGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service("singerService")
public class SingerServiceImpl implements SingerService {

    private final SingerRepo singerRepo;

    public SingerServiceImpl(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll() {
        return singerRepo.findAll(Sort.unsorted());
    }

    @Override
    public void delete(Long id) {
        singerRepo.deleteById(id);
    }

    @Override
    public Singer findById(Long id) {
        return singerRepo.findById(id).orElseThrow(() -> new NotFoundException(Singer.class, id));
    }

    @Override
    public Singer save(Singer singer) {
       return singerRepo.save(singer);
    }

    @Override
    public Page<Singer> findAllByPage(Pageable pageable) {
        return singerRepo.findAll(pageable);
    }

    @Override
    public List<Singer> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException {
        var fg = FieldGroup.getField(criteria.getFieldName());
        Iterable<Singer> result =
                switch (fg) {
                    case FIRSTNAME -> criteria.getExactMatch() ? singerRepo.findByFirstName(criteria.getFieldValue())
                            : singerRepo.findByFirstNameLike(criteria.getFieldValue());

                    case LASTNAME -> criteria.getExactMatch() ? singerRepo.findByLastName(criteria.getFieldValue())
                            : singerRepo.findByLastNameLike(criteria.getFieldValue());

                    case BIRTHDATE -> {
                        LocalDate date;
                        try {
                            date = LocalDate.parse(criteria.getFieldValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } catch (DateTimeParseException e) {
                            throw new InvalidCriteriaException("fieldValue", "typeMismatch.hiringDate");
                        }
                        yield singerRepo.findByBirthDate(date);
                    }
                };
        return StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
    }
}