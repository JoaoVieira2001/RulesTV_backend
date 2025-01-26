package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Serie;
import com.RulesTV.RulesTV.repositories.SerieRepository;
import com.RulesTV.RulesTV.rest.DTO.SerieDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serie")
public class SerieController {

    private final SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    @GetMapping("/all")
    public List<SerieDTO> getAllSeries(){
        List<Serie> series = serieRepository.findAll();
        return series.stream().map(serie -> {
            SerieDTO dto = new SerieDTO(serie.getTitle(),serie.getDescription(),serie.getTrailerUrl(),serie.getNumberSeasons(),serie.getCertifications(),
                    serie.getReleaseDate(),serie.getRating(),serie.getStatus().name(),serie.getLanguage(),serie.getPoster());
            dto.setTitle(serie.getTitle());
            dto.setDescription(serie.getDescription());
            dto.setStatus(serie.getStatus().name());
            dto.setTrailer_url(serie.getTrailerUrl());
            dto.setNumber_seasons(serie.getNumberSeasons());
            dto.setRelease_date(serie.getReleaseDate());
            dto.setRating(serie.getRating());
            dto.setLanguage(serie.getLanguage());
            dto.setCertifications(serie.getCertifications());
            dto.setPoster(serie.getPoster());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieDTO> getSerieById(@PathVariable int id) {
        return serieRepository.findById(id)
                .map(serie -> {
                    SerieDTO dto = new SerieDTO(
                            serie.getTitle(),
                            serie.getDescription(),
                            serie.getTrailerUrl(),
                            serie.getNumberSeasons(),
                            serie.getCertifications(),
                            serie.getReleaseDate(),
                            serie.getRating(),
                            serie.getStatus().name(),
                            serie.getLanguage(),
                            serie.getPoster()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new RuntimeException("Serie with ID " + id + " not found"));
    }


    @PostMapping("/post")
    public ResponseEntity<Serie> createSerie(@RequestBody Serie serie){
        Serie savedSerie = serieRepository.save(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSerie);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<SerieDTO> updateSerie(@PathVariable int id, @RequestBody SerieDTO updatedSerieDTO) {
        return serieRepository.findById(id)
                .map(existingSerie -> {
                    // Update fields only if they are not null
                    if (updatedSerieDTO.getTitle() != null) {
                        existingSerie.setTitle(updatedSerieDTO.getTitle());
                    }
                    if (updatedSerieDTO.getDescription() != null) {
                        existingSerie.setDescription(updatedSerieDTO.getDescription());
                    }
                    if (updatedSerieDTO.getTrailer_url() != null) {
                        existingSerie.setTrailerUrl(updatedSerieDTO.getTrailer_url());
                    }
                    if (updatedSerieDTO.getRelease_date() != null) {
                        existingSerie.setReleaseDate(updatedSerieDTO.getRelease_date());
                    }
                    if (updatedSerieDTO.getLanguage() != null) {
                        existingSerie.setLanguage(updatedSerieDTO.getLanguage());
                    }
                    if (updatedSerieDTO.getPoster() != null) {
                        existingSerie.setPoster(updatedSerieDTO.getPoster());
                    }

                    // Always update non-null fields
                    if (updatedSerieDTO.getCertifications() != null) {
                        existingSerie.setCertifications(updatedSerieDTO.getCertifications());
                    }
                    if (updatedSerieDTO.getRating() != null) {
                        existingSerie.setRating(updatedSerieDTO.getRating());
                    }
                    if (updatedSerieDTO.getNumber_seasons() != null) {
                        existingSerie.setNumberSeasons(updatedSerieDTO.getNumber_seasons());
                    }

                    if (updatedSerieDTO.getStatus() != null) {
                        try {
                            Serie.Status status = Serie.Status.valueOf(updatedSerieDTO.getStatus().toUpperCase());
                            existingSerie.setStatus(status);
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Invalid status value: " + updatedSerieDTO.getStatus() +
                                    ". Options: ON_GOING, COMPLETED, NOT_VIEWED.");
                        }
                    }

                    Serie savedSerie = serieRepository.save(existingSerie);

                    SerieDTO dto = new SerieDTO(
                            savedSerie.getTitle(),
                            savedSerie.getDescription(),
                            savedSerie.getTrailerUrl(),
                            savedSerie.getNumberSeasons(),
                            savedSerie.getCertifications(),
                            savedSerie.getReleaseDate(),
                            savedSerie.getRating(),
                            savedSerie.getStatus().name(),
                            savedSerie.getLanguage(),
                            savedSerie.getPoster()
                    );

                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new RuntimeException("Serie with ID " + id + " not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSerie(@PathVariable int id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            return ResponseEntity.ok("The Serie with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serie with ID " + id + " not found.");
        }
    }
}
