package com.apress.prospring6.twelve.spring.repos;

import com.apress.prospring6.twelve.spring.entities.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car,Long> {
}
