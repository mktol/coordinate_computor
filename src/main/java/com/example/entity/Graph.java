package com.example.entity;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<City> nodes = new HashSet<>();
    private Set<Snippet> vertex = new HashSet<>();

    public Graph(Set<City> nodes, Set<Snippet> vertex) {
        this.nodes = nodes;
        this.vertex = vertex;
    }

    public void addCoordinate(City city){
        nodes.add(city);
    }

    public Set<City> getNodes() {
        return nodes;
    }

    public void setNodes(Set<City> nodes) {
        this.nodes = nodes;
    }

    public Set<Snippet> getVertex() {
        return vertex;
    }

    public void setVertex(Set<Snippet> vertex) {
        this.vertex = vertex;
    }
}
