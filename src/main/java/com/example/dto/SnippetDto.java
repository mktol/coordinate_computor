package com.example.dto;

import java.util.Objects;

public class SnippetDto {
    private Long id;

    private Long departure;
    private Long arrival;
    private CityDto start;
    private CityDto finish;

    public Long duration(){
        return arrival-departure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeparture() {
        return departure;
    }

    public void setDeparture(Long departure) {
        this.departure = departure;
    }

    public Long getArrival() {
        return arrival;
    }

    public void setArrival(Long arrival) {
        this.arrival = arrival;
    }

    public CityDto getStart() {
        return start;
    }

    public void setStart(CityDto start) {
        this.start = start;
    }

    public CityDto getFinish() {
        return finish;
    }

    public void setFinish(CityDto finish) {
        this.finish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SnippetDto)) return false;
        SnippetDto that = (SnippetDto) o;
        return  Objects.equals(departure, that.departure) &&
                Objects.equals(arrival, that.arrival) &&
                Objects.equals(start, that.start) &&
                Objects.equals(finish, that.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departure, arrival, start, finish);
    }
}
