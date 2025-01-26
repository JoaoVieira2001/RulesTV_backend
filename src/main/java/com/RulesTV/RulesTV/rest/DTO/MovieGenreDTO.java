package com.RulesTV.RulesTV.rest.DTO;

public class MovieGenreDTO {

    private String movie_title;
    private String genre_name;

    public MovieGenreDTO(String movie_title, String genre_name) {
        this.movie_title = movie_title;
        this.genre_name = genre_name;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
