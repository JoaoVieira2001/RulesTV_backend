package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.CastPeople;
import com.RulesTV.RulesTV.entity.MovieGenre;
import com.RulesTV.RulesTV.entity.SerieGenre;
import com.RulesTV.RulesTV.repositories.MovieGenreRepository;
import com.RulesTV.RulesTV.rest.DTO.MovieGenreDTO;
import com.RulesTV.RulesTV.rest.DTO.SerieGenreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie_genre")
public class MovieGenreController {

    private final MovieGenreRepository movieGenreRepository;

    public MovieGenreController(MovieGenreRepository movieGenreRepository){
        this.movieGenreRepository = movieGenreRepository;
    }

    @GetMapping("/all")
    public List<MovieGenreDTO> getAllMovieGenres(){
        List<MovieGenre> movie_genres = movieGenreRepository.findAll();
        return movie_genres.stream().map(movie_genre -> {
            MovieGenreDTO dto = new MovieGenreDTO(movie_genre.getMovie().getTitle(),movie_genre.getGenre().getName());
            dto.setGenre_name(movie_genre.getGenre().getName());
            dto.setMovie_title(movie_genre.getMovie().getTitle());
            return dto;
        }).collect(Collectors.toList());    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieGenreDTO> getMovieGenreById(@PathVariable int id){
        return movieGenreRepository.findById(id)
                .map(movie_genre -> {
                    MovieGenreDTO dto = new MovieGenreDTO(movie_genre.getMovie().getTitle(),movie_genre.getGenre().getName());
                    dto.setGenre_name(movie_genre.getGenre().getName());
                    dto.setMovie_title(movie_genre.getMovie().getTitle());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());    }

    @PostMapping("/post")
    public ResponseEntity<MovieGenre> createMovieGenre(@RequestBody MovieGenre movie){
        MovieGenre savedMovie = movieGenreRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MovieGenre> updateMovieGenre(@PathVariable int id, @RequestBody MovieGenre updatedMovieGenre) {
        return movieGenreRepository.findById(id)
                .map(existingMovieGenre -> {
                    existingMovieGenre.setMovie(updatedMovieGenre.getMovie());
                    existingMovieGenre.setGenre(updatedMovieGenre.getGenre());
                    movieGenreRepository.save(existingMovieGenre);
                    return ResponseEntity.ok(existingMovieGenre);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieGenre(@PathVariable int id) {
        if (movieGenreRepository.existsById(id)) {
            movieGenreRepository.deleteById(id);
            return ResponseEntity.ok("The MovieGenre with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MovieGenre with ID " + id + " not found.");
        }
    }
}
