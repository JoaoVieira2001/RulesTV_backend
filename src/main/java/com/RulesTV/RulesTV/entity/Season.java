package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @Column
    private int lastEpisodeWatched;

    @Column
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private int numberEpisodes;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Episode> episodeList;

    @ManyToOne
    @JoinColumn(name = "serie_id", referencedColumnName = "id")
    private Serie serie;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getLastEpisodeWatched() {
        return lastEpisodeWatched;
    }

    public void setLastEpisodeWatched(Integer lastEpisodeWatched) {
        this.lastEpisodeWatched = lastEpisodeWatched;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getNumberEpisodes() {
        return numberEpisodes;
    }

    public void setNumberEpisodes(Integer numberEpisodes) {
        this.numberEpisodes = numberEpisodes;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }


}
