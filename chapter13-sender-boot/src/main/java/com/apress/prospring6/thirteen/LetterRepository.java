package com.apress.prospring6.thirteen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "mailbox",path = "letters")
public interface LetterRepository extends JpaRepository<Letter,Long> {

    @RestResource(path = "byCategory",rel="customFindMethod")
    List<Letter> findByCategory(@Param("category") Category category);
    List<Letter> findBySentOn(@Param("data")LocalDate sentOn);

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
}
