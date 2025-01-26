package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import org.springframework.core.metrics.StartupStep;

@Entity
public class SerieTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }


}
