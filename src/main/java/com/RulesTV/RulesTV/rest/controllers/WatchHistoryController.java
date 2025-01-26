package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.WatchHistory;
import com.RulesTV.RulesTV.repositories.WatchHistoryRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.SubscriptionPlanDTO;
import com.RulesTV.RulesTV.rest.DTO.WatchHistoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watch_history")
public class WatchHistoryController {

    private final WatchHistoryRepository watchHistoryRepository;

    public WatchHistoryController(WatchHistoryRepository watchHistoryRepository){
        this.watchHistoryRepository = watchHistoryRepository;
    }

    @GetMapping("/all")
    public List<WatchHistoryDTO> getAllWatchHistory(){
        List<WatchHistory> watch_histories = watchHistoryRepository.findAll();
        return watch_histories.stream().map(watch_history -> {
            WatchHistoryDTO dto = new WatchHistoryDTO(
                    watch_history.getWatchedAt(),
                    watch_history.getProgress(),
                    watch_history.getLocation(),
                    watch_history.getDeviceType().name(),
                    watch_history.getProfile() != null ? watch_history.getProfile().getName() : null,
                    watch_history.getSerie() != null ? watch_history.getSerie().getTitle() : null,
                    watch_history.getMovie() != null ? watch_history.getMovie().getTitle() : null
            );

            dto.setWatched_at(watch_history.getWatchedAt());
            dto.setProgress(watch_history.getProgress());
            dto.setLocation(watch_history.getLocation());
            dto.setDevice_type(watch_history.getDeviceType().name());
            dto.setProfile_name(watch_history.getProfile() != null ? watch_history.getProfile().getName() : null);
            dto.setSerie_title(watch_history.getSerie() != null ? watch_history.getSerie().getTitle() : null);
            dto.setMovie_title(watch_history.getMovie() != null ? watch_history.getMovie().getTitle() : null);

            return dto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<WatchHistoryDTO> getWatchHistoryById(@PathVariable int id) {
        WatchHistory watchHistory = watchHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WatchHistory with ID " + id + " not found"));

        WatchHistoryDTO dto = new WatchHistoryDTO(
                watchHistory.getWatchedAt(),
                watchHistory.getProgress(),
                watchHistory.getLocation(),
                watchHistory.getDeviceType().name(),
                watchHistory.getProfile() != null ? watchHistory.getProfile().getName() : null,
                watchHistory.getSerie() != null ? watchHistory.getSerie().getTitle() : null,
                watchHistory.getMovie() != null ? watchHistory.getMovie().getTitle() : null
        );

        dto.setWatched_at(watchHistory.getWatchedAt());
        dto.setProgress(watchHistory.getProgress());
        dto.setLocation(watchHistory.getLocation());
        dto.setDevice_type(watchHistory.getDeviceType().name());
        dto.setProfile_name(watchHistory.getProfile() != null ? watchHistory.getProfile().getName() : null);
        dto.setSerie_title(watchHistory.getSerie() != null ? watchHistory.getSerie().getTitle() : null);
        dto.setMovie_title(watchHistory.getMovie() != null ? watchHistory.getMovie().getTitle() : null);

        return ResponseEntity.ok(dto);
    }


    @PostMapping("/post")
    public ResponseEntity<WatchHistory> createWatchHistory(@RequestBody WatchHistory watchHistory){
        WatchHistory savedWatchHistory = watchHistoryRepository.save(watchHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWatchHistory);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<WatchHistoryDTO> updateWatchHistory(@PathVariable int id, @RequestBody WatchHistory updatedWatchHistory) {
        return watchHistoryRepository.findById(id)
                .map(existingWatchHistory -> {
                    if (updatedWatchHistory.getWatchedAt() != null) {
                        existingWatchHistory.setWatchedAt(updatedWatchHistory.getWatchedAt());
                    }
                    if (updatedWatchHistory.getProgress() != null) {
                        existingWatchHistory.setProgress(updatedWatchHistory.getProgress());
                    }
                    if (updatedWatchHistory.getLocation() != null) {
                        existingWatchHistory.setLocation(updatedWatchHistory.getLocation());
                    }
                    if (updatedWatchHistory.getDeviceType() != null) {
                        existingWatchHistory.setDeviceType(updatedWatchHistory.getDeviceType());
                    }
                    if (updatedWatchHistory.getSerie() != null) {
                        existingWatchHistory.setSerie(updatedWatchHistory.getSerie());
                    }
                    if (updatedWatchHistory.getMovie() != null) {
                        existingWatchHistory.setMovie(updatedWatchHistory.getMovie());
                    }
                    if (updatedWatchHistory.getProfile() != null) {
                        existingWatchHistory.setProfile(updatedWatchHistory.getProfile());
                    }
                    watchHistoryRepository.save(existingWatchHistory);

                    WatchHistoryDTO dto = new WatchHistoryDTO(
                            existingWatchHistory.getWatchedAt(),
                            existingWatchHistory.getProgress(),
                            existingWatchHistory.getLocation(),
                            existingWatchHistory.getDeviceType().name(),
                            existingWatchHistory.getProfile() != null ? existingWatchHistory.getProfile().getName() : null,
                            existingWatchHistory.getSerie() != null ? existingWatchHistory.getSerie().getTitle() : null,
                            existingWatchHistory.getMovie() != null ? existingWatchHistory.getMovie().getTitle() : null
                    );

                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new RuntimeException("WatchHistory with ID " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWatchHistory(@PathVariable int id) {
        if (watchHistoryRepository.existsById(id)) {
            watchHistoryRepository.deleteById(id);
            return ResponseEntity.ok("The WatchHistory with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WatchHistory with ID " + id + " not found.");
        }
    }
}
