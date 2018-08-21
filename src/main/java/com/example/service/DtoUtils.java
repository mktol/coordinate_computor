package com.example.service;

import com.example.dto.CityDto;
import com.example.dto.SnippetDto;
import com.example.entity.City;
import com.example.entity.Snippet;

import java.util.List;
import java.util.stream.Collectors;

public class DtoUtils {


    public static CityDto toDto(City city){
        CityDto dto = new CityDto();
        dto.setName(city.getCityName());
        dto.setId(city.getId());
        return dto;
    }


    public static City toEntity(CityDto dto){
        City city = new City(dto.getName());
        if(dto.getId()!=null && dto.getId()>=0){
            city.setId(dto.getId());
        }
        return city;
    }

    public static Snippet toEntity(SnippetDto dto){
        City start = toEntity(dto.getStart());
        City finish = toEntity(dto.getFinish());
        Long departure = dto.getDeparture();
        Long arrival = dto.getArrival();
        Snippet snippet = new Snippet(start, finish, departure, arrival);
        if(dto.getId()!=null && dto.getId()>=0){
            snippet.setId(dto.getId());
        }
        return  snippet;
    }

    public static SnippetDto toDto(Snippet snippet){
        SnippetDto dto = new SnippetDto();
        dto.setStart(toDto(snippet.getStart()));
        dto.setFinish(toDto(snippet.getFinish()));
        dto.setArrival(snippet.getArrival());
        dto.setDeparture(snippet.getDeparture());
        dto.setId(snippet.getId());
        return dto;
    }

    public static List<CityDto> toDto(List<City> cities){
        return cities.stream().map(DtoUtils::toDto).collect(Collectors.toList());
    }

    public static List<SnippetDto> toDtoList(List<Snippet> all) {
        return all.stream().map(DtoUtils::toDto).collect(Collectors.toList());
    }
}
