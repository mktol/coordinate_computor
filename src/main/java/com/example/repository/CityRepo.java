package com.example.repository;

import com.example.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {

    City findByCityName(String name);

    @Override
    List<City> findAll();
}
