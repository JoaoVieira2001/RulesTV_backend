package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Genre;
import com.RulesTV.RulesTV.entity.Serie;
import com.RulesTV.RulesTV.entity.SerieGenre;
import com.RulesTV.RulesTV.repositories.SerieGenreRepository;
import com.RulesTV.RulesTV.rest.DTO.GenreDTO;
import com.RulesTV.RulesTV.rest.DTO.SerieGenreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serie_genre")
public class SerieGenreController {

    private final SerieGenreRepository serieGenreRepository;

    public SerieGenreController(SerieGenreRepository serieGenreRepository){
        this.serieGenreRepository = serieGenreRepository;
    }

    @GetMapping("/all")
    public List<SerieGenreDTO> getAllSerieGenres(){
        List<SerieGenre> serie_genres = serieGenreRepository.findAll();
        return serie_genres.stream().map(serie_genre -> {
            SerieGenreDTO dto = new SerieGenreDTO(serie_genre.getSerie().getTitle(),serie_genre.getGenre().getName());
            dto.setGenre_name(serie_genre.getGenre().getName());
            dto.setSerie_title(serie_genre.getSerie().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SerieGenreDTO> getSerieGenreById(@PathVariable int id) {
        return serieGenreRepository.findById(id)
                .map(serie_genre -> {
                    SerieGenreDTO dto = new SerieGenreDTO(serie_genre.getSerie().getTitle(),serie_genre.getGenre().getName());
                    dto.setGenre_name(serie_genre.getGenre().getName());
                    dto.setSerie_title(serie_genre.getSerie().getTitle());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<SerieGenre> createSerieGenre(@RequestBody SerieGenre serieGenre){
        SerieGenre savedSerieGenre = serieGenreRepository.save(serieGenre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSerieGenre);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<SerieGenre> updateSerieGenre(@PathVariable int id, @RequestBody SerieGenre updatedSerieGenre) {
        return serieGenreRepository.findById(id)
                .map(existingSerieGenre -> {
                    existingSerieGenre.setSerie(updatedSerieGenre.getSerie());
                    existingSerieGenre.setGenre(updatedSerieGenre.getGenre());
                    serieGenreRepository.save(existingSerieGenre);
                    return ResponseEntity.ok(existingSerieGenre);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSerieGenre(@PathVariable int id) {
        if (serieGenreRepository.existsById(id)) {
            serieGenreRepository.deleteById(id);
            return ResponseEntity.ok("The SerieGenre with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SerieGenre with ID " + id + " not found.");
        }
    }
}
