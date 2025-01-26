package com.RulesTV.RulesTV.rest.DTO;

public class MovieTagDTO {

    private String movie_title;
    private String tag_name;

    public MovieTagDTO(String movie_title, String tag_name) {
        this.movie_title = movie_title;
        this.tag_name = tag_name;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
