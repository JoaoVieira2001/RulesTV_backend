package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.Movie;
import com.RulesTV.RulesTV.repositories.EpisodeRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.MovieDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository){
        this.episodeRepository = episodeRepository;
    }

    @GetMapping("/all")
    public List<EpisodeDTO> getAllEpisodes(){
        List<Episode> episodes = episodeRepository.findAll();
        return episodes.stream().map(episode -> {
            EpisodeDTO dto = new EpisodeDTO(episode.getTitle(),episode.getDescription(),episode.getEpisode_number(),episode.getDuration_minutes(),episode.getSeason().getNumber(),episode.getSerie().getTitle(),episode.getReleaseDate(),episode.getRuntime(),episode.getRuntime_override(),episode.getStatus().name());
            dto.setDescription(episode.getDescription());
            dto.setEpisode_number(episode.getEpisode_number());
            dto.setRuntime(episode.getRuntime());
            dto.setRelease_date(episode.getReleaseDate());
            dto.setDuration_minutes(episode.getDuration_minutes());
            dto.setStatus(episode.getStatus().name());
            dto.setSeason_number(episode.getSeason().getNumber());
            dto.setSerie_title(episode.getSerie().getTitle());
            dto.setRuntime_override(episode.getRuntime_override());
            dto.setTitle(episode.getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDTO> getEpisodeById(@PathVariable int id) {
        return episodeRepository.findById(id)
                .map(episode -> {
                    EpisodeDTO dto = new EpisodeDTO(episode.getTitle(),episode.getDescription(),episode.getEpisode_number(),episode.getDuration_minutes(),episode.getSeason().getNumber(),episode.getSerie().getTitle(),episode.getReleaseDate(),episode.getRuntime(),episode.getRuntime_override(),episode.getStatus().name());
                    dto.setDescription(episode.getDescription());
                    dto.setEpisode_number(episode.getEpisode_number());
                    dto.setRuntime(episode.getRuntime());
                    dto.setRelease_date(episode.getReleaseDate());
                    dto.setDuration_minutes(episode.getDuration_minutes());
                    dto.setStatus(episode.getStatus().name());
                    dto.setSeason_number(episode.getSeason().getNumber());
                    dto.setSerie_title(episode.getSerie().getTitle());
                    dto.setRuntime_override(episode.getRuntime_override());
                    dto.setTitle(episode.getTitle());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<Episode> createEpisode(@RequestBody Episode episode){
        Episode savedEpisode = episodeRepository.save(episode);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEpisode);
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateEpisode(@PathVariable int id, @RequestBody EpisodeDTO updatedEpisodeDTO) {
        return episodeRepository.findById(id)
                .map(existingEpisode -> {
                    existingEpisode.setTitle(updatedEpisodeDTO.getTitle());
                    existingEpisode.setDescription(updatedEpisodeDTO.getDescription());
                    existingEpisode.setReleaseDate(updatedEpisodeDTO.getRelease_date());
                    existingEpisode.setEpisode_number(updatedEpisodeDTO.getEpisode_number());
                    existingEpisode.setDuration_minutes(updatedEpisodeDTO.getDuration_minutes());
                    existingEpisode.setRuntime(updatedEpisodeDTO.getRuntime());
                    existingEpisode.setRuntime_override(updatedEpisodeDTO.getRuntime_override());

                    Episode.Status status;
                    try {
                        status = Episode.Status.valueOf(updatedEpisodeDTO.getStatus().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body("Invalid status value: " + updatedEpisodeDTO.getStatus() + "\n" + "Options: ON_GOING,COMPLETED,NOT_VIEWED");
                    }
                    existingEpisode.setStatus(status);

                    Episode savedEpisode = episodeRepository.save(existingEpisode);

                    EpisodeDTO responseDTO = new EpisodeDTO(savedEpisode.getTitle(),savedEpisode.getDescription(),savedEpisode.getEpisode_number(),savedEpisode.getDuration_minutes()
                                                            ,savedEpisode.getSeason().getNumber(),savedEpisode.getSerie().getTitle(),savedEpisode.getReleaseDate(),savedEpisode.getRuntime(),
                                                            savedEpisode.getRuntime_override(),savedEpisode.getStatus().name());
                    responseDTO.setTitle(savedEpisode.getTitle());
                    responseDTO.setDescription(savedEpisode.getDescription());
                    responseDTO.setRelease_date(savedEpisode.getReleaseDate());
                    responseDTO.setEpisode_number(savedEpisode.getEpisode_number());
                    responseDTO.setDuration_minutes(savedEpisode.getDuration_minutes());
                    responseDTO.setRuntime(savedEpisode.getRuntime());
                    responseDTO.setRuntime_override(savedEpisode.getRuntime_override());
                    responseDTO.setStatus(savedEpisode.getStatus().name());
                    responseDTO.setSeason_number(savedEpisode.getSeason().getNumber());
                    responseDTO.setSerie_title(savedEpisode.getSerie().getTitle());

                    return ResponseEntity.ok(responseDTO);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEpisode(@PathVariable int id) {
        if (episodeRepository.existsById(id)) {
            episodeRepository.deleteById(id);
            return ResponseEntity.ok("The cast with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cast with ID " + id + " not found.");
        }
    }
}
