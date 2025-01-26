package com.RulesTV.RulesTV.rest.DTO;

public class SerieGenreDTO {
    private String serie_title;
    private String genre_name;

    public SerieGenreDTO(String serie_title, String genre_name) {
        this.serie_title = serie_title;
        this.genre_name = genre_name;
    }

    public String getSerie_title() {
        return serie_title;
    }

    public void setSerie_title(String serie_title) {
        this.serie_title = serie_title;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
