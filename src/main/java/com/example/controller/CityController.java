package com.example.controller;

import com.example.core.Calculator;
import com.example.dto.CityDto;
import com.example.exception.CityNotFoundException;
import com.example.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @RequestMapping("/citie")
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody CityDto cityDto){
        return cityService.save(cityDto);
    }
    @GetMapping("/cities/name/{name}")
    public CityDto find(@PathVariable String name){
        return cityService.findByName(name);
    }
    @GetMapping("/cities/{id}")
    public CityDto findById(@PathVariable String id){
        return cityService.findById(id);
    }

    @PostMapping("/cities/path/time")
    public List<CityDto> getBestRoute(@RequestBody Map<String, CityDto> cityDtoMap){
        CityDto start = cityDtoMap.get("start");
        CityDto finish = cityDtoMap.get("finish");
        if(start==null && finish==null){
            throw new CityNotFoundException("Start or finis is empty");
        }

        Objects.requireNonNull(start);
        Objects.requireNonNull(finish);
        return cityService.findPath(start, finish);
    }
    @GetMapping("temp/time")
    public LocalDateTime getLocalDataTime(){
        return LocalDateTime.now();
    }
    @GetMapping("temp/date")
    public LocalDate getLocalData(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

        LocalDate now = LocalDate.now();
        return now;
    }
    @GetMapping("temp/date/stirng")
    public String getLocalDataString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
