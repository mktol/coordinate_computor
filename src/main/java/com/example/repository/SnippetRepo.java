package com.example.repository;

import com.example.entity.City;
import com.example.entity.Snippet;
import javafx.scene.effect.SepiaTone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface SnippetRepo extends CrudRepository<Snippet, Long> {

    Snippet findByStart_CityNameAndFinish_CityName(String startCityName, String finishCityName);

    Snippet removeById(Long id);

    @Override
    List<Snippet> findAll();
}
