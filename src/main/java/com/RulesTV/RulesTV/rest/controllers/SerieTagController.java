package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.SerieGenre;
import com.RulesTV.RulesTV.entity.SerieTag;
import com.RulesTV.RulesTV.repositories.SerieTagRepository;
import com.RulesTV.RulesTV.rest.DTO.SerieGenreDTO;
import com.RulesTV.RulesTV.rest.DTO.SerieTagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serie_tag")
public class SerieTagController {

    private final SerieTagRepository serieTagRepository;

    public SerieTagController(SerieTagRepository serieTagRepository){
        this.serieTagRepository = serieTagRepository;
    }

    @GetMapping("/all")
    public List<SerieTagDTO> getAllTagGenres(){
        List<SerieTag> serie_tags = serieTagRepository.findAll();
        return serie_tags.stream().map(serie_tag -> {
            SerieTagDTO dto = new SerieTagDTO(serie_tag.getSerie().getTitle(),serie_tag.getTag().getName());
            dto.setTag_name(serie_tag.getTag().getName());
            dto.setSerie_title(serie_tag.getSerie().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SerieTagDTO> getSerieTagById(@PathVariable int id) {
        SerieTag serieTag = serieTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SerieTag with ID " + id + " not found"));

        SerieTagDTO dto = new SerieTagDTO(
                serieTag.getSerie().getTitle(),
                serieTag.getTag().getName()
        );
        dto.setTag_name(serieTag.getTag().getName());
        dto.setSerie_title(serieTag.getSerie().getTitle());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/post")
    public ResponseEntity<SerieTag> createSerieTag(@RequestBody SerieTag serieTag){
        SerieTag savedSerieTag = serieTagRepository.save(serieTag);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSerieTag);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<SerieTag> updateSerieTag(@PathVariable int id, @RequestBody SerieTag updatedSerieTag) {
        return serieTagRepository.findById(id)
                .map(existingSerieTag -> {
                    existingSerieTag.setSerie(updatedSerieTag.getSerie());
                    existingSerieTag.setTag(updatedSerieTag.getTag());
                    serieTagRepository.save(existingSerieTag);
                    return ResponseEntity.ok(existingSerieTag);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSerieTag(@PathVariable int id) {
        if (serieTagRepository.existsById(id)) {
            serieTagRepository.deleteById(id);
            return ResponseEntity.ok("The SerieTag with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SerieTag with ID " + id + " not found.");
        }
    }
}
