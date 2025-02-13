package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Genre;
import com.RulesTV.RulesTV.repositories.GenreRepository;
import com.RulesTV.RulesTV.rest.DTO.GenreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @GetMapping("/all")
    public List<GenreDTO> getAllGenres(){
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genre -> {
            GenreDTO dto = new GenreDTO(genre.getName());
            dto.setName(genre.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable int id) {
        return genreRepository.findById(id)
                .map(genre -> {
                    GenreDTO dto = new GenreDTO(genre.getName());
                    dto.setName(genre.getName());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/post")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable int id, @RequestBody Genre updatedGenre) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    existingGenre.setName(updatedGenre.getName());
                    genreRepository.save(existingGenre);
                    return ResponseEntity.ok(existingGenre);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return ResponseEntity.ok("The Genre with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre with ID " + id + " not found.");
        }
    }
}
