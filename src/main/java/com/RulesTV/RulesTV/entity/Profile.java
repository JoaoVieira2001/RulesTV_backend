package com.RulesTV.RulesTV.entity;
import com.RulesTV.RulesTV.configs.UserPreferencesConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(unique = true, length = 100)
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @Convert(converter = UserPreferencesConverter.class)
    @Column
    private UserPreferences userPreferences;

    @Column(nullable = false)
    private int age;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isKidsProfile;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UserAuth userAuth;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<WatchHistory> watchHistoryList;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Library> libraryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isKidsProfile() {
        return isKidsProfile;
    }

    public void setKidsProfile(boolean kidsProfile) {
        isKidsProfile = kidsProfile;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getKidsProfile() {
        return isKidsProfile;
    }

    public void setKidsProfile(Boolean kidsProfile) {
        isKidsProfile = kidsProfile;
    }

    public UserAuth getUserEmail() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public void setWatchHistoryList(List<WatchHistory> watchHistoryList){
        this.watchHistoryList = watchHistoryList;
    }

    public List<WatchHistory> getWatchHistoryList(){
        return watchHistoryList;
    }

    public void setLibraryList(List<Library> libraryList){
        this.libraryList = libraryList;
    }

    public List<Library> getLibraryList(){
        return libraryList;
    }

}
