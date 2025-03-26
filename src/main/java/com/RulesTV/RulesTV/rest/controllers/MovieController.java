package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Movie;
import com.RulesTV.RulesTV.entity.WatchHistory;
import com.RulesTV.RulesTV.repositories.MovieRepository;
import com.RulesTV.RulesTV.rest.DTO.MovieDTO;
import com.RulesTV.RulesTV.rest.DTO.WatchHistoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping("/all")
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            MovieDTO dto = new MovieDTO(
                    movie.getTitle(),
                    movie.getDescription(),
                    movie.getReleaseDate(),
                    movie.getRuntime(),
                    movie.getDurationMinutes(),
                    movie.getAudio(),
                    movie.getSubtitle(),
                    movie.getRating(),
                    movie.getPoster(),
                    movie.getTrailerUrl(),
                    movie.getCertifications(),
        null
                    );

            if (movie.getWatchHistory() != null) {
                WatchHistory watchHistory = movie.getWatchHistory();
                WatchHistoryDTO watchHistoryDTO = new WatchHistoryDTO(
                        watchHistory.getWatchedAt(),
                        watchHistory.getProgress(),
                        watchHistory.getLocation(),
                        watchHistory.getDeviceType().name(),
                        watchHistory.getProfile() != null ? watchHistory.getProfile().getName() : null,
                        watchHistory.getSerie() != null ? watchHistory.getSerie().getTitle() : null,
                        movie.getTitle()
                );
                dto.setWatchHistory(watchHistoryDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable int id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    MovieDTO dto = new MovieDTO(
                            movie.getTitle(),
                            movie.getDescription(),
                            movie.getReleaseDate(),
                            movie.getRuntime(),
                            movie.getDurationMinutes(),
                            movie.getAudio(),
                            movie.getSubtitle(),
                            movie.getRating(),
                            movie.getPoster(),
                            movie.getTrailerUrl(),
                            movie.getCertifications(),
                            null
                    );

                    if (movie.getWatchHistory() != null) {
                        WatchHistory watchHistory = movie.getWatchHistory();
                        WatchHistoryDTO watchHistoryDTO = new WatchHistoryDTO(
                                watchHistory.getWatchedAt(),
                                watchHistory.getProgress(),
                                watchHistory.getLocation(),
                                watchHistory.getDeviceType().name(),
                                watchHistory.getProfile() != null ? watchHistory.getProfile().getName() : null,
                                watchHistory.getSerie() != null ? watchHistory.getSerie().getTitle() : null,
                                movie.getTitle()
                        );
                        dto.setWatchHistory(watchHistoryDTO); // Set watch history in DTO
                    }

                    return ResponseEntity.ok(dto); // Return MovieDTO wrapped in ResponseEntity
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Handle movie not found
    }



    @PostMapping("/post")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    existingMovie.setTitle(updatedMovie.getTitle());
                    existingMovie.setDescription(updatedMovie.getDescription());
                    existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
                    existingMovie.setDurationMinutes(updatedMovie.getDurationMinutes());
                    existingMovie.setAudio(updatedMovie.getAudio());
                    existingMovie.setSubtitle(updatedMovie.getSubtitle());
                    existingMovie.setRating(updatedMovie.getRating());
                    existingMovie.setPoster(updatedMovie.getPoster());
                    existingMovie.setTrailerUrl(updatedMovie.getTrailerUrl());
                    existingMovie.setCertifications(updatedMovie.getCertifications());
                    existingMovie.setRuntime(updatedMovie.getRuntime());
                    existingMovie.setCastList(updatedMovie.getCastList());
                    existingMovie.setReviewList(updatedMovie.getReviewList());
                    movieRepository.save(existingMovie);
                    return ResponseEntity.ok(existingMovie);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return ResponseEntity.ok("The movie with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID " + id + " not found.");
        }
    }
}
