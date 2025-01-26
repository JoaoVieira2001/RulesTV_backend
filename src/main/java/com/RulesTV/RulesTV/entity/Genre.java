package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<SerieGenre> seriesGenres;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<MovieGenre> moviesGenres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SerieGenre> getSeriesGenres() {
        return seriesGenres;
    }

    public void setSeriesGenres(List<SerieGenre> seriesGenres) {
        this.seriesGenres = seriesGenres;
    }

    public List<MovieGenre> getMoviesGenres() {
        return moviesGenres;
    }

    public void setMoviesGenres(List<MovieGenre> moviesGenres) {
        this.moviesGenres = moviesGenres;
    }
}
