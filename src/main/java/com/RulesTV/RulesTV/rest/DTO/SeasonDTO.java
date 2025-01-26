package com.RulesTV.RulesTV.rest.DTO;
import java.time.LocalDateTime;

public class SeasonDTO {
    private int number;
    private int last_ep_watched;
    private LocalDateTime release_date;
    private int number_episodes;

    public SeasonDTO(int number, int last_ep_watched, LocalDateTime release_date, int number_episodes) {
        this.number = number;
        this.last_ep_watched = last_ep_watched;
        this.release_date = release_date;
        this.number_episodes = number_episodes;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public int getLast_ep_watched() {
        return last_ep_watched;
    }

    public void setLast_ep_watched(int last_ep_watched) {
        this.last_ep_watched = last_ep_watched;
    }

    public LocalDateTime getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateTime release_date) {
        this.release_date = release_date;
    }

    public int getNumber_episodes() {
        return number_episodes;
    }

    public void setNumber_episodes(int number_episodes) {
        this.number_episodes = number_episodes;
    }
}
