package com.example;

import com.example.core.Calculator;
import com.example.core.ShortPathCalculator;
import com.example.entity.City;
import com.example.entity.Graph;
import com.example.entity.Snippet;
import com.example.repository.CityRepo;
import com.example.repository.SnippetRepo;
import com.example.service.SnippetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@EnableEurekaClient
@SpringBootApplication
public class CoordinateCoreApp {
    public static void main(String[] args) {
        SpringApplication.run(CoordinateCoreApp.class, args);
    }

    @Bean
    public CommandLineRunner demo(CityRepo cityRepo, SnippetRepo snippetRepo, /*Calculator calculator,*/ SnippetService snippetService) {
        return (args) -> {
            // save a couple of customers
//            testDeikstra(cityRepo, snippetService, calculator);
            testDeikstra2(cityRepo, snippetRepo/*, calculator*/);

        };
    }

    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Bean
    public Calculator calculator(){
        return new Calculator();
    }
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Bean
    public ShortPathCalculator shortPathCalculator(){
        return new ShortPathCalculator();
    }

    private void testDeikstra(final CityRepo cityRepo, final SnippetService snippetService, Calculator calculator) {
        List<City> nodes = createCoordinates();
        List<Snippet> edges = new ArrayList<>();
        Set<City> savedCoordinates = StreamSupport.stream(cityRepo.saveAll(nodes).spliterator(),false).collect(Collectors.toSet());

        addChunk(0, 1, 1000, 1085, nodes, edges);
        addChunk(0, 2, 1000, 1217, nodes, edges);
        addChunk(0, 4, 1000, 1173, nodes, edges);
        addChunk(2, 6, 1000, 1186, nodes, edges);
        addChunk(2, 7, 1000, 1103, nodes, edges);
        addChunk(3, 7, 1000, 1183, nodes, edges);
        addChunk(5, 8, 1000, 1250, nodes, edges);
        addChunk(8, 9, 1000, 1084, nodes, edges);
        addChunk(7, 9, 1000, 1167, nodes, edges);
        addChunk(4, 9, 1000, 1502, nodes, edges);
        addChunk(9, 10, 1000, 1040, nodes, edges);
        addChunk(1, 10, 1000, 1600, nodes, edges);
//        Set<Snippet> savedSnippets =
        Iterable<Snippet> snippets = snippetService.saveAll(edges);
        List<Snippet> snippetSet = StreamSupport.stream(snippets.spliterator(), false).collect(Collectors.toList());
//        Graph graph = new Graph(savedCoordinates, snippetSet );
//        calculator.setGraph(graph);
        calculator.execute(nodes.get(0), snippetSet );
        LinkedList<City> path = calculator.getPath(nodes.get(10));
        Objects.requireNonNull(path);
        path.forEach(p-> System.out.println("path: "+p));

        System.out.println("-------------------------------------------------------");



    }

    public void testDeikstra2(final CityRepo cityRepo, final SnippetRepo snippetRepo/*, Calculator calculator*/){
        List<City> nodes=new ArrayList<>();
        List<Snippet> edges = new ArrayList<>();

        City nodeA=new City("A");
        City nodeB=new City("B");
        City nodeC=new City("C");
        City nodeD=new City("D");
        City nodeE=new City("E");
        City nodeF=new City("F");

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);
        nodes.add(nodeE);
        nodes.add(nodeF);

        Set<City> savedCoordinates = StreamSupport.stream(cityRepo.saveAll(nodes).spliterator(),false).collect(Collectors.toSet());


        Snippet snippet1 = new Snippet(nodeA, nodeB, 300L,310L);
        Snippet snippet2 = new Snippet(nodeA, nodeC, 300L,315L);
        Snippet snippet3 = new Snippet(nodeB, nodeD, 300L,312L);
        Snippet snippet4 = new Snippet(nodeB, nodeF, 300L,315L);
        Snippet snippet5 = new Snippet(nodeC, nodeE, 300L,310L);
//        Snippet snippet6 = new Snippet(nodeD, nodeE, 300L,302L);
        Snippet snippet7 = new Snippet(nodeD, nodeF, 300L,301L);
        Snippet snippet8 = new Snippet(nodeF, nodeE, 300L,305L);

        edges.add(snippet1);
        edges.add(snippet2);
        edges.add(snippet3);
        edges.add(snippet4);
        edges.add(snippet5);
//        edges.add(snippet6);
        edges.add(snippet7);
        edges.add(snippet8);

        Iterable<Snippet> snippets = snippetRepo.saveAll(edges);
        List<Snippet> snippetList = StreamSupport.stream(snippets.spliterator(), false).collect(Collectors.toList());
//        Graph graph = new Graph(savedCoordinates, snippetSet );
//        calculator.setGraph(graph);

/*        calculator.execute(nodeA, snippetList);
        LinkedList<City> path = calculator.getPath(nodeE);
        path.forEach(p-> System.out.println(p));*/


    }

    private void addChunk(int currentNodeId, int neghborNode, long departmentTime, long arriveTime, List<City> cities, List<Snippet> snippets) {
        Snippet snippet = new Snippet(cities.get(currentNodeId), cities.get(neghborNode), departmentTime, arriveTime);
        snippets.add(snippet);
    }

    private List<City> createCoordinates() {
        List<City> nodes=  new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            City city = new City("city_"+i);
            nodes.add(city);
        }
        return nodes;
    }
}
