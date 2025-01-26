package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Genre;
import com.RulesTV.RulesTV.entity.Tag;
import com.RulesTV.RulesTV.repositories.TagRepository;
import com.RulesTV.RulesTV.rest.DTO.GenreDTO;
import com.RulesTV.RulesTV.rest.DTO.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @GetMapping("/all")
    public List<TagDTO> getAllTags(){
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> {
            TagDTO dto = new TagDTO(tag.getName());
            dto.setName(tag.getName());
            return dto;
        }).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable int id) {
        return tagRepository.findById(id)
                .map(tag -> {
                    TagDTO dto = new TagDTO(tag.getName());
                    dto.setName(tag.getName());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        Tag savedTag = tagRepository.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable int id, @RequestBody Tag updatedTag) {
        return tagRepository.findById(id)
                .map(existingTag -> {
                    existingTag.setName(updatedTag.getName());
                    existingTag.setDescription(updatedTag.getDescription());
                    existingTag.setSerieTagList(updatedTag.getSerieTagList());
                    existingTag.setMovieTagList(updatedTag.getMovieTagList());
                    tagRepository.save(existingTag);
                    return ResponseEntity.ok(existingTag);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable int id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return ResponseEntity.ok("The Tag with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag with ID " + id + " not found.");
        }
    }
}
