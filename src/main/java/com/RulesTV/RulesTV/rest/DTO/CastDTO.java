package com.RulesTV.RulesTV.rest.DTO;

public class CastDTO {

    private int id;
    private String name;
    private String role;
    private String profile_url;
    private String character_name;
    private String biography;
    private String movie_title;
    private String serie_title;

    public CastDTO(int id, String name, String role, String profile_url, String character_name, String biography, String movie_title, String serie_title) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.profile_url = profile_url;
        this.character_name = character_name;
        this.biography = biography;
        this.movie_title = movie_title;
        this.serie_title = serie_title;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getSerie_title() {
        return serie_title;
    }

    public void setSerie_title(String serie_title) {
        this.serie_title = serie_title;
    }
}
