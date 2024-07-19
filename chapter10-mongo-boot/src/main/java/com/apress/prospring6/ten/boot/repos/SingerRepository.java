package com.apress.prospring6.ten.boot.repos;

import com.apress.prospring6.ten.boot.document.Singer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SingerRepository extends MongoRepository<Singer, String> {

    Iterable<Singer> findByFirstName(String firstName);
}
