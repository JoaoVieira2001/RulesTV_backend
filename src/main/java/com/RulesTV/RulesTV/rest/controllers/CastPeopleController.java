package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.CastPeople;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.repositories.CastPeopleRepository;
import com.RulesTV.RulesTV.rest.DTO.CastDTO;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cast")
public class CastPeopleController {

    private final CastPeopleRepository castPeopleRepository;

    public CastPeopleController(CastPeopleRepository castPeopleRepository){
        this.castPeopleRepository = castPeopleRepository;
    }

    @GetMapping("/all")
    public List<CastDTO> getAllCast(){
        List<CastPeople> casts = castPeopleRepository.findAll();
        return casts.stream().map(cast -> {
            CastDTO dto = new CastDTO(cast.getId(),cast.getName(),cast.getRole(),cast.getProfile_url(),cast.getCharacter_name(),cast.getBiography(),cast.getMovie().getTitle(),cast.getSerie().getTitle());
            dto.setId(cast.getId());
            dto.setName(cast.getName());
            dto.setRole(cast.getRole());
            dto.setProfile_url(cast.getProfile_url());
            dto.setCharacter_name(cast.getCharacter_name());
            dto.setBiography(cast.getBiography());
            dto.setMovie_title(cast.getMovie().getTitle());
            dto.setSerie_title(cast.getSerie().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastDTO> getCastById(@PathVariable int id){
        return castPeopleRepository.findById(id)
                .map(cast -> {
                    CastDTO dto = new CastDTO(cast.getId(),cast.getName(),cast.getRole(),cast.getProfile_url(),cast.getCharacter_name(),cast.getBiography(),cast.getMovie().getTitle(),cast.getSerie().getTitle());
                    dto.setId(cast.getId());
                    dto.setName(cast.getName());
                    dto.setRole(cast.getRole());
                    dto.setProfile_url(cast.getProfile_url());
                    dto.setCharacter_name(cast.getCharacter_name());
                    dto.setBiography(cast.getBiography());
                    dto.setMovie_title(cast.getMovie().getTitle());
                    dto.setSerie_title(cast.getSerie().getTitle());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<CastPeople> createCast(@RequestBody CastPeople castPeople){
        CastPeople savedCast = castPeopleRepository.save(castPeople);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCast);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<CastDTO> updateCast(@PathVariable int id, @RequestBody CastDTO updatedCastDTO) {
        return castPeopleRepository.findById(id)
                .map(existingCast -> {
                    existingCast.setName(updatedCastDTO.getName());
                    existingCast.setRole(updatedCastDTO.getRole());
                    existingCast.setProfile_url(updatedCastDTO.getProfile_url());
                    existingCast.setCharacter_name(updatedCastDTO.getCharacter_name());
                    existingCast.setBiography(updatedCastDTO.getBiography());

                    CastPeople savedCast = castPeopleRepository.save(existingCast);

                    CastDTO savedCastDTO = new CastDTO(
                            savedCast.getId(),
                            savedCast.getName(),
                            savedCast.getRole(),
                            savedCast.getProfile_url(),
                            savedCast.getCharacter_name(),
                            savedCast.getBiography(),
                            savedCast.getMovie() != null ? savedCast.getMovie().getTitle() : null,
                            savedCast.getSerie() != null ? savedCast.getSerie().getTitle() : null
                    );
                    return ResponseEntity.ok(savedCastDTO);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCast(@PathVariable int id) {
        if (castPeopleRepository.existsById(id)) {
            castPeopleRepository.deleteById(id);
            return ResponseEntity.ok("The cast with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cast with ID " + id + " not found.");
        }
    }
}
