package com.example.core;

import com.example.dto.CityDto;
import com.example.dto.SnippetDto;
import com.example.entity.City;
import com.example.entity.Graph;
import com.example.entity.Snippet;

import java.util.*;



public class ShortPathCalculator {

    private List<SnippetDto> edges;
    private Set<CityDto> settledNodes;
    private Set<CityDto> unSettledNodes;
    private Map<CityDto, CityDto> predecessors;
    private Map<CityDto, Long> distance;

    public ShortPathCalculator() {

    }

/*    public void setGraph(Graph graph){
        this.edges = new ArrayList<>(graph.getVertex());
    }*/


    public void execute(CityDto source, List<SnippetDto> SnippetDtos){
        edges = new ArrayList<>(SnippetDtos);
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        predecessors = new HashMap<>();
        distance = new HashMap<>();

        distance.put(source, 0L);
        unSettledNodes.add(source);

        while (unSettledNodes.size() > 0){
            CityDto node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistance(node);
        }
    }

    private void findMinimalDistance(CityDto node) {
        List<CityDto> adjacentNodes = getNeighbors(node);
        for (CityDto target : adjacentNodes) {
            if(getShortestDistance(target) > getShortestDistance(node)+getDistance(node, target)){
                distance.put(target, getShortestDistance(node)+getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private long getDistance(CityDto node, CityDto target) {
        for (SnippetDto edge : edges) {
            if(edge.getStart().equals(node) && edge.getFinish().equals(target)){
                return edge.duration();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<CityDto> getNeighbors(CityDto node) {
        List<CityDto> neighbors= new ArrayList<>();
        for (SnippetDto edge : edges) {
            if(edge.getStart().equals(node) && !isSettled(edge.getFinish())){
                neighbors.add(edge.getFinish());
            }
        }
        return neighbors;
    }

    private boolean isSettled(CityDto city) {
        return settledNodes.contains(city);
    }

    private long getShortestDistance(CityDto target) {
        Long distance = this.distance.get(target);
        return distance == null ? Long.MAX_VALUE : distance;
    }

    private CityDto getMinimum(Set<CityDto> nodesSet) {
        CityDto min = null;
        for (CityDto city : nodesSet) {
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

    public LinkedList<CityDto> getPath(CityDto target){
        LinkedList<CityDto> path = new LinkedList<>();
        CityDto step = target;

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
