package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.CastPeople;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.Library;
import com.RulesTV.RulesTV.repositories.LibraryRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.LibraryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryRepository libraryRepository;

    public LibraryController(LibraryRepository libraryRepository){
        this.libraryRepository = libraryRepository;
    }

    @GetMapping("/all")
    public List<LibraryDTO> getAllLibraries(){
        List<Library> libraries = libraryRepository.findAll();
        return libraries.stream().map(library -> {
            LibraryDTO dto = new LibraryDTO(
                    library.getId(),
                    library.getAddedAt(),
                    library.getCategory(),
                    library.getContent_id(),
                    library.getContent_type().name(),
                    library.getProfile().getName());
            dto.setId(library.getId());
            dto.setAdded_at(library.getAddedAt());
            dto.setCategory(library.getCategory());
            dto.setContent_id(library.getContent_id());
            dto.setContent_type(library.getContent_type().name());
            dto.setProfile_name(library.getProfile().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable int id) {
        return libraryRepository.findById(id)
                .map(library -> {
                    LibraryDTO dto = new LibraryDTO(
                            library.getId(),
                            library.getAddedAt(),
                            library.getCategory(),
                            library.getContent_id(),
                            library.getContent_type().name(),
                            library.getProfile().getName()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/post")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library){
        Library savedLibrary = libraryRepository.save(library);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLibrary);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<LibraryDTO> updateLibrary(@PathVariable int id, @RequestBody LibraryDTO updatedLibraryDTO) {
        return libraryRepository.findById(id)
                .map(existingLibrary -> {
                    existingLibrary.setAddedAt(updatedLibraryDTO.getAdded_at());
                    existingLibrary.setCategory(updatedLibraryDTO.getCategory());
                    existingLibrary.setContent_id(updatedLibraryDTO.getContent_id());

                    Library.Content_Type content_type = Library.Content_Type.valueOf(updatedLibraryDTO.getContent_type().toUpperCase());
                    existingLibrary.setContent_type(content_type);
                    Library savedLibrary = libraryRepository.save(existingLibrary);

                    LibraryDTO responseDTO = new LibraryDTO(
                            savedLibrary.getId(),
                            savedLibrary.getAddedAt(),
                            savedLibrary.getCategory(),
                            savedLibrary.getContent_id(),
                            savedLibrary.getContent_type().name(),
                            savedLibrary.getProfile().getName()
                    );

                    return ResponseEntity.ok(responseDTO);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable int id) {
        if (libraryRepository.existsById(id)) {
            libraryRepository.deleteById(id);
            return ResponseEntity.ok("The cast with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cast with ID " + id + " not found.");
        }
    }
}
