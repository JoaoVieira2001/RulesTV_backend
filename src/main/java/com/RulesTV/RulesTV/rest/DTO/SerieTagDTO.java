package com.RulesTV.RulesTV.rest.DTO;

public class SerieTagDTO {

    private String serie_title;
    private String tag_name;

    public SerieTagDTO(String serie_title, String tag_name) {
        this.serie_title = serie_title;
        this.tag_name = tag_name;
    }

    public String getSerie_title() {
        return serie_title;
    }

    public void setSerie_title(String serie_title) {
        this.serie_title = serie_title;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
