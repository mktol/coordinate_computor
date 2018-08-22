package com.example.service;

import com.example.dto.SnippetDto;
import com.example.entity.City;
import com.example.entity.Snippet;
import com.example.exception.NoSnippedException;
import com.example.repository.SnippetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.service.DtoUtils.toDto;
import static com.example.service.DtoUtils.toDtoList;
import static com.example.service.DtoUtils.toEntity;

@Service
public class SnippetService {

    private SnippetRepo snippetRepo;
    private CityService cityService;

    @Autowired
    public SnippetService(SnippetRepo snippetRepo, CityService cityService) {
        this.snippetRepo = snippetRepo;
        this.cityService = cityService;
    }

    public Snippet find(String snippetId){
        validate(snippetId);
        Optional<Snippet> snipped = snippetRepo.findById(Long.parseLong(snippetId));
        return snipped.orElseThrow(()->new NoSnippedException("No snipeped with id: "+snippetId));
    }

    private void validate(String snippetId) {
        //TODO
    }

    @Transactional
    public Iterable<Snippet> saveAll(Iterable<Snippet> snippets){
        return snippetRepo.saveAll(snippets);
    }


    //TODO think about return type. Is it necessary ?
    @Transactional
    public SnippetDto save(SnippetDto snippetDto) {
        Snippet snippet = toEntity(snippetDto);
        City start = snippet.getStart();
        City finish = snippet.getFinish();
        Snippet existedCity = snippetRepo.findByStart_CityNameAndFinish_CityName(start.getCityName(), finish.getCityName());
        if(existedCity!=null){
            return toDto(existedCity);
        }
        start = cityService.save(start);
        finish = cityService.save(finish);
        snippet.setStart(start);
        snippet.setFinish(finish);
        Snippet savedSnipped = snippetRepo.save(snippet);
        return toDto(savedSnipped);
    }


    public void remove(Long id) {
        Optional<Snippet> optional = snippetRepo.findById(id);
        if(optional.isPresent()) {
            snippetRepo.deleteById(id);
        }
        // do nothing
    }

    public List<SnippetDto> findAll() {
        return toDtoList(snippetRepo.findAll());
    }


}

