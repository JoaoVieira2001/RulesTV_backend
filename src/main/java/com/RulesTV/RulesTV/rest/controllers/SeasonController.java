package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.*;
import com.RulesTV.RulesTV.repositories.SeasonRepository;
import com.RulesTV.RulesTV.rest.DTO.CastDTO;
import com.RulesTV.RulesTV.rest.DTO.MovieDTO;
import com.RulesTV.RulesTV.rest.DTO.SeasonDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/season")
public class SeasonController {

    private final SeasonRepository seasonRepository;

    public SeasonController(SeasonRepository seasonRepository){
        this.seasonRepository = seasonRepository;
    }

    @GetMapping("/all")
    public List<SeasonDTO> getAllSeasons(){
        List<Season> seasons = seasonRepository.findAll();
        return seasons.stream().map(season -> {
            SeasonDTO dto = new SeasonDTO(season.getNumber(),season.getLastEpisodeWatched(),season.getReleaseDate(),season.getNumberEpisodes());
            dto.setNumber(season.getNumber());
            dto.setRelease_date(season.getReleaseDate());
            dto.setLast_ep_watched(season.getLastEpisodeWatched());
            dto.setNumber_episodes(season.getNumberEpisodes());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDTO> getSeasonById(@PathVariable int id){
        Season season = seasonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Season with ID " + id + " not found"));

        SeasonDTO dto = new SeasonDTO(season.getNumber(),
                                      season.getLastEpisodeWatched(),
                                      season.getReleaseDate(),
                                      season.getNumberEpisodes());
            dto.setNumber(season.getNumber());
            dto.setRelease_date(season.getReleaseDate());
            dto.setLast_ep_watched(season.getLastEpisodeWatched());
            dto.setNumber_episodes(season.getNumberEpisodes());

        return ResponseEntity.ok(dto);

    }

    @PostMapping("/post")
    public ResponseEntity<Season> createSeason(@RequestBody Season season){
        if(season.getLastEpisodeWatched() > season.getNumberEpisodes()){
            throw new IllegalArgumentException("Last episode watched cannot be greater than the number of episodes.");
        }
        Season savedSeason = seasonRepository.save(season);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSeason);
    }

    @PutMapping("/put/{number}")
    public ResponseEntity<Season> updateSeason(@PathVariable int number, @RequestBody Season updatedSeason) {
        return seasonRepository.findById(number)
                .map(existingSeason -> {
                    if (updatedSeason.getLastEpisodeWatched() > updatedSeason.getNumberEpisodes()) {
                        throw new IllegalArgumentException("Last episode watched cannot be greater than the number of episodes.");
                    }

                    if (updatedSeason.getLastEpisodeWatched() != null) {
                        existingSeason.setLastEpisodeWatched(updatedSeason.getLastEpisodeWatched());
                    }
                    if (updatedSeason.getReleaseDate() != null) {
                        existingSeason.setReleaseDate(updatedSeason.getReleaseDate());
                    }
                    if (updatedSeason.getNumberEpisodes() != null) {
                        existingSeason.setNumberEpisodes(updatedSeason.getNumberEpisodes());
                    }
                    if (updatedSeason.getEpisodeList() != null) {
                        existingSeason.setEpisodeList(updatedSeason.getEpisodeList());
                    }
                    if (updatedSeason.getSerie() != null) {
                        existingSeason.setSerie(updatedSeason.getSerie());
                    }
                    Season savedSeason = seasonRepository.save(existingSeason);

                    return ResponseEntity.ok(savedSeason);
                }).orElseGet(() -> ResponseEntity.notFound().build());
}

    @DeleteMapping("/delete/{number}")
    public ResponseEntity<String> deleteSeason(@PathVariable int number) {
        if (seasonRepository.existsById(number)) {
            seasonRepository.deleteById(number);
            return ResponseEntity.ok("The Season with number " + number + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Season with number " + number + " not found.");
        }
    }
}
