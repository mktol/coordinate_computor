package com.example.entity;

import javax.persistence.*;
import java.util.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityName;

    @Transient
    private static long maxTime = Long.MAX_VALUE;

    @OneToMany(mappedBy = "start", fetch = FetchType.LAZY)
    private List<Snippet> startSnippet= new ArrayList<>();

    @OneToMany(mappedBy = "finish", fetch = FetchType.LAZY)
    private List<Snippet> finishSnippet = new ArrayList<>();

    public City() {
    }

    public City(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public List<Snippet> getStartSnippet() {
        return startSnippet;
    }

    public void setStartSnippet(List<Snippet> startSnippet) {
        this.startSnippet = startSnippet;
    }

    public List<Snippet> getFinishSnippet() {
        return finishSnippet;
    }

    public void setFinishSnippet(List<Snippet> finishSnippet) {
        this.finishSnippet = finishSnippet;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(getCityName(), city.getCityName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCityName());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
