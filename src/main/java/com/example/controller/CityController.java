package com.example.controller;

import com.example.dto.CityDto;
import com.example.exception.CityNotFoundException;
import com.example.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping("/cities")
    @ResponseStatus(HttpStatus.CREATED)
    public CityDto save(@RequestBody CityDto cityDto) {
        return cityService.save(cityDto);
    }

    @GetMapping("/cities/search")
    public CityDto find(@RequestParam String name) {
        return cityService.findByName(name);
    }

    @GetMapping("/cities/")
    public List<CityDto> getAll() {
        return cityService.findAll();
    }

    @GetMapping("/cities/{id}")
    public CityDto findById(@PathVariable String id) {
        return cityService.findById(id);
    }

    @GetMapping("/cities/path/time")
    public List<CityDto> getBestRouteByTime(@RequestParam("start") String start, @RequestParam("finish") String finish) {
        return cityService.findShortestPathByTime(start, finish);
    }

    @GetMapping("/cities/path/points")
    public List<CityDto> getBestRouteByPoints(@RequestParam("start") String start, @RequestParam("finish") String finish) {
        return cityService.findShortestPathByCities(start, finish);
    }

    @GetMapping("temp/date/stirng")
    public String getLocalDataString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
