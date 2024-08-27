package com.apress.prospring6.fourteen.boot.repos;

import com.apress.prospring6.fourteen.boot.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepo extends JpaRepository<Instrument, String> {
}
