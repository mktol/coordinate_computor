package com.example.service;

import com.example.core.Calculator;
import com.example.core.ShortPathCalculator;
import com.example.dto.CityDto;
import com.example.dto.SnippetDto;
import com.example.entity.City;
import com.example.entity.Snippet;
import com.example.exception.CityNotFoundException;
import com.example.exception.PathNotFoundException;
import com.example.repository.CityRepo;
import com.example.repository.SnippetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.service.DtoUtils.toDto;
import static com.example.service.DtoUtils.toEntity;

@Service
public class CityService {

    private final CityRepo cityRepo;
    private SnippetRepo snippetRepo;
    @Resource(name = "calculator")
    private Calculator calculator;
    @Resource(name = "shortPathCalculator")
    private ShortPathCalculator shortPathCalculator;

    @Autowired
    public CityService(CityRepo cityRepo, SnippetRepo snippetRepo) {
        this.cityRepo = cityRepo;
        this.snippetRepo = snippetRepo;
    }

    public City save(City city) {
        City existedCity = cityRepo.findByCityName(city.getCityName());
        return existedCity != null ? existedCity : cityRepo.save(city);
    }

    public CityDto save(CityDto cityDto) {
        City city = toEntity(cityDto);
        City savedCity = save(city);
        return toDto(savedCity);
    }

    public CityDto findByName(String name) {
        if(name==null || name.isEmpty()){
            throw new CityNotFoundException("City name is empty");
        }
        City city = cityRepo.findByCityName(name);
        if (city == null) {
            throw new CityNotFoundException("city with name: " + name + " does not exist.");
        }
        return toDto(city);
    }

    public CityDto findById(String id) {
        Optional<City> city = cityRepo.findById(Long.parseLong(id));
        return toDto(city.orElseThrow(() -> new CityNotFoundException("Can not find city with id: " + id)));
    }



    public List<CityDto> findPath(final CityDto start, final CityDto finish, boolean isTimeIgnored ) {
        if(start==null || finish == null){
            throw new  CityNotFoundException("Start city or finish city is not stored");
        }

        Iterable<Snippet> snippets = snippetRepo.findAll();
        List<SnippetDto> allSnippetsDto = StreamSupport.stream(snippets.spliterator(), false).map(DtoUtils::toDto).collect(Collectors.toList());
        shortPathCalculator.execute(start, allSnippetsDto, isTimeIgnored);
        LinkedList<CityDto> path = shortPathCalculator.getPath(finish);

        if (path == null || path.isEmpty()) {
            throw new PathNotFoundException("Can not find path for cities start: " + start.getName() + ", finish: " + finish.getName());
        }
        return path;
    }

    private boolean isCityInDb(String cityName) {
        City byCityName = cityRepo.findByCityName(cityName);
        return byCityName != null;
    }

    public List<CityDto> findAll() {
        return toDto(cityRepo.findAll());
    }

    public List<CityDto> findShortestPathByTime(final String start, final String finish){
        CityDto startPoint = findByName(start);
        CityDto finishPoint = findByName(finish);
        return findPath(startPoint, finishPoint, false);
    }

    @Transactional
    public List<CityDto> findShortestPathByCities(String start, String finish) {
        CityDto startPoint = findByName(start);
        CityDto finishPoint = findByName(finish);
        return findPath(startPoint, finishPoint, true);

    }
}
