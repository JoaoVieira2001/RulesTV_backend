package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.Profile;
import com.RulesTV.RulesTV.repositories.ProfileRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.ProfileDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @GetMapping("/all")
    public List<ProfileDTO> getAllProfiles(){
        List<Profile> profiles = profileRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();

        return profiles.stream().map(profile -> {
            String userPreferencesJSON = null;
            try {
                //ObjectMapper serializes UserPreferences to a JSON string
                userPreferencesJSON = objectMapper.writeValueAsString(profile.getUserPreferences());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            ProfileDTO dto = new ProfileDTO(profile.getName(),profile.getEmail(),userPreferencesJSON,profile.getAge(),profile.getCreatedAt(),profile.getUpdatedAt(),profile.getKidsProfile(),profile.getUserEmail().getEmail());
            dto.setName(profile.getName());
            dto.setEmail(profile.getEmail());
            dto.setUser_preferences(userPreferencesJSON);
            dto.setAge(profile.getAge());
            dto.setCreated_at(profile.getCreatedAt());
            dto.setUpdate_at(profile.getUpdatedAt());
            dto.setIs_kids_profile(profile.getKidsProfile());
            dto.setAccount_email(profile.getUserEmail().getEmail());
            return dto;
        }).collect(Collectors.toList());    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable int id){
        ObjectMapper objectMapper = new ObjectMapper();

        return profileRepository.findById(id)
                .map(profile -> {
                    String userPreferencesJSON = null;
                    try {
                        //ObjectMapper serializes UserPreferences to a JSON string
                        userPreferencesJSON = objectMapper.writeValueAsString(profile.getUserPreferences());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    ProfileDTO dto = new ProfileDTO(profile.getName(),profile.getEmail(),userPreferencesJSON,profile.getAge(),profile.getCreatedAt(),profile.getUpdatedAt(),profile.getKidsProfile(),profile.getUserEmail().getEmail());
                    dto.setName(profile.getName());
                    dto.setEmail(profile.getEmail());
                    dto.setUser_preferences(userPreferencesJSON);
                    dto.setAge(profile.getAge());
                    dto.setCreated_at(profile.getCreatedAt());
                    dto.setUpdate_at(profile.getUpdatedAt());
                    dto.setIs_kids_profile(profile.getKidsProfile());
                    dto.setAccount_email(profile.getUserEmail().getEmail());

                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile){
        Profile savedProfile = profileRepository.save(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable int id, @RequestBody Profile updatedProfile) {
        return profileRepository.findById(id)
                .map(existingProfile -> {
                    existingProfile.setName(updatedProfile.getName());
                    existingProfile.setUserPreferences(updatedProfile.getUserPreferences());
                    existingProfile.setAge(updatedProfile.getAge());
                    existingProfile.setCreatedAt(updatedProfile.getCreatedAt());
                    existingProfile.setUpdatedAt(updatedProfile.getUpdatedAt());
                    existingProfile.setKidsProfile(updatedProfile.getKidsProfile());
                    existingProfile.setUserAuth(updatedProfile.getUserEmail());
                    existingProfile.setWatchHistoryList(updatedProfile.getWatchHistoryList());
                    existingProfile.setLibraryList(updatedProfile.getLibraryList());
                    profileRepository.save(existingProfile);
                    return ResponseEntity.ok(existingProfile);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return ResponseEntity.ok("The Profile with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile with ID " + id + " not found.");
        }
    }
}
