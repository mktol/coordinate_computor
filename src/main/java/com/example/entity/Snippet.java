package com.example.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Snippet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "START_ID")
    private City start;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINISH_ID")
    private City finish;
    private Long departure;
    private Long arrival;

    public Snippet() {
    }

    public Snippet(City start, City finish, Long departure, Long arrival) {
        this.start = start;
        this.finish = finish;
        this.departure = departure;
        this.arrival = arrival;
//        start.adjacentNodes.put(finish, duration());
    }

    // weight
    public long duration(){
        return arrival-departure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getStart() {
        return start;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public City getFinish() {
        return finish;
    }

    public void setFinish(City finish) {
        this.finish = finish;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snippet)) return false;
        Snippet snippet = (Snippet) o;
        return Objects.equals(start, snippet.start) &&
                Objects.equals(finish, snippet.finish) &&
                Objects.equals(departure, snippet.departure) &&
                Objects.equals(arrival, snippet.arrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish, departure, arrival);
    }

    @Override
    public String toString() {
        return "SnippetRepo{" +
                "id=" + id +
                ", start=" + start +
                ", finish=" + finish +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }
}
