package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<SerieTag> serieTagList;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<MovieTag> movieTagList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SerieTag> getSerieTagList() {
        return serieTagList;
    }

    public void setSerieTagList(List<SerieTag> serieTagList) {
        this.serieTagList = serieTagList;
    }

    public List<MovieTag> getMovieTagList() {
        return movieTagList;
    }

    public void setMovieTagList(List<MovieTag> movieTagList) {
        this.movieTagList = movieTagList;
    }

}
