package com.example.core;

import com.example.entity.City;
import com.example.entity.Graph;
import com.example.entity.Snippet;

import java.util.*;


public class Calculator {

    private List<Snippet> edges;
    private Set<City> settledNodes;
    private Set<City> unSettledNodes;
    private Map<City, City> predecessors;
    private Map<City, Long> distance;

    public Calculator() {

    }

    public void execute(City source, List<Snippet> snippets, boolean isTimeIgnored){
        edges = new ArrayList<>(snippets);
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        predecessors = new HashMap<>();
        distance = new HashMap<>();

        distance.put(source, 0L);
        unSettledNodes.add(source);

        while (unSettledNodes.size() > 0){
            City node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistance(node, isTimeIgnored);
        }
    }

    private void findMinimalDistance(City node, boolean isTimeIgnored) {
        List<City> adjacentNodes = getNeighbors(node);
        if(isTimeIgnored){
            calculateDistanceIgnoreTime(node, adjacentNodes);
        }else {
            calculateDistanceWithTime(node, adjacentNodes);
        }
    }

    private void calculateDistanceWithTime(City node, List<City> adjacentNodes) {
        for (City target : adjacentNodes) {
            if(getShortestDistance(target) > getShortestDistance(node)+getDistance(node, target)){
                distance.put(target, getShortestDistance(node)+getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }

        }
    }

    private void calculateDistanceIgnoreTime(City node, List<City> adjacentNodes) {
        for (City target : adjacentNodes) {
            if(getShortestDistance(target) > getShortestDistance(node)+getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + 1);
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

//    private void calculateDistanceWithTime(City node, City target) {
//        if(getShortestDistance(target) > getShortestDistance(node)+getDistance(node, target)){
//            distance.put(target, getShortestDistance(node)+getDistance(node, target));
//            predecessors.put(target, node);
//            unSettledNodes.add(target);
//        }
//    }

    private long getDistance(City node, City target) {
        for (Snippet edge : edges) {
            if(edge.getStart().equals(node) && edge.getFinish().equals(target)){
//                return 1L;
                return edge.duration();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<City> getNeighbors(City node) {
        List<City> neighbors= new ArrayList<>();
        for (Snippet edge : edges) {
            if(edge.getStart().equals(node) && !isSettled(edge.getFinish())){
                neighbors.add(edge.getFinish());
            }
        }
        return neighbors;
    }

    private boolean isSettled(City city) {
        return settledNodes.contains(city);
    }

    private long getShortestDistance(City target) {
        Long distance = this.distance.get(target);
        return distance == null ? Long.MAX_VALUE : distance;
    }

    private City getMinimum(Set<City> nodesSet) {
        City min = null;
        for (City city : nodesSet) {
            if(min == null){
                min = city;
            }else{
                if(getShortestDistance(city) < getShortestDistance(min)){
                    min= city;
                }
            }
        }
        return min;
    }

    public LinkedList<City> getPath(City target){
        LinkedList<City> path = new LinkedList<>();
        City step = target;

        if(predecessors.get(step)==null){
            return null;
        }
        path.add(step);
        while (predecessors.get(step)!=null){
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
