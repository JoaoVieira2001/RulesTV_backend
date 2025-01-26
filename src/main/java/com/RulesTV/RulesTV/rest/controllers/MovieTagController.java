package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.CastPeople;
import com.RulesTV.RulesTV.entity.MovieGenre;
import com.RulesTV.RulesTV.entity.MovieTag;
import com.RulesTV.RulesTV.entity.SerieTag;
import com.RulesTV.RulesTV.repositories.MovieTagRepository;
import com.RulesTV.RulesTV.rest.DTO.MovieGenreDTO;
import com.RulesTV.RulesTV.rest.DTO.MovieTagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie_tag")
public class MovieTagController {

    private final MovieTagRepository movieTagRepository;

    public MovieTagController(MovieTagRepository movieTagRepository){
        this.movieTagRepository = movieTagRepository;
    }

    @GetMapping("/all")
    public List<MovieTagDTO> getAllMovieTags() {
        List<MovieTag> movie_tags = movieTagRepository.findAll();
        return movie_tags.stream().map(movie_tag -> {
            MovieTagDTO dto = new MovieTagDTO(
                    movie_tag.getMovie().getTitle(),
                    movie_tag.getTag().getName()
            );
            return dto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieTagDTO> getMovieTagById(@PathVariable int id){
        MovieTag movieTag = movieTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MovieTag with ID " + id + " not found"));

        MovieTagDTO dto = new MovieTagDTO(
                movieTag.getMovie().getTitle(),
                movieTag.getTag().getName()
        );
        dto.setTag_name(movieTag.getTag().getName());
        dto.setMovie_title(movieTag.getMovie().getTitle());
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/post")
    public ResponseEntity<MovieTag> createMovieTag(@RequestBody MovieTag movie){
        MovieTag savedMovie = movieTagRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MovieTag> updateMovieTag(@PathVariable int id, @RequestBody MovieTag updatedMovieTag) {
        return movieTagRepository.findById(id)
                .map(existingMovieTag -> {
                    existingMovieTag.setMovie(updatedMovieTag.getMovie());
                    existingMovieTag.setTag(updatedMovieTag.getTag());
                    movieTagRepository.save(existingMovieTag);
                    return ResponseEntity.ok(existingMovieTag);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieTag(@PathVariable int id) {
        if (movieTagRepository.existsById(id)) {
            movieTagRepository.deleteById(id);
            return ResponseEntity.ok("The MovieTag with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MovieTag with ID " + id + " not found.");
        }
    }
}
