package com.apress.prospring6.twelve.spring.service;

import com.apress.prospring6.twelve.spring.entities.Car;

import java.util.stream.Stream;

public interface CarService {
    Stream<Car> findAll();
    Car save(Car car);
    void updateCarAgeJob();
    boolean isDone();
}
