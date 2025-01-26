package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime addedAt;

    @Column(nullable = false, length = 255)
    private String category;

    @Column(nullable = false)
    private int content_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Content_Type content_type;

    public enum Content_Type {
        SERIE,
        MOVIE,
        EPISODE;
    }

    @ManyToOne
    @JoinColumn(name = "profile_name", referencedColumnName = "name")


    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAddedAt() { return addedAt;}

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public Content_Type getContent_type() {
        return content_type;
    }

    public void setContent_type(Content_Type content_type) {
        this.content_type = content_type;
    }


}
