package com.apress.prospring6.seventeen.boot.repos;

import com.apress.prospring6.seventeen.boot.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepo extends JpaRepository<Instrument,String> {
}
