package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuthLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserAuth user;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private String sessionId;

   /* @Column(nullable = false)
    private String geoLocation; // Storing latitude and longitude as a string
*/
    @Column(nullable = false)
    private LocalDateTime loginTime;

    @Column(nullable = false)
    private String browser;

    @Column(nullable = false)
    private LocalDateTime sessionExpiry;

    public AuthLogin() {
    }

    public AuthLogin(UserAuth user, String ipAddress, String userAgent, String sessionId, LocalDateTime loginTime, String browser, LocalDateTime sessionExpiry) {

        this.user = user;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.sessionId = sessionId;
        this.loginTime = loginTime;
        this.browser = browser;
        this.sessionExpiry = sessionExpiry;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserAuth getUser() {
        return user;
    }

    public void setUser(UserAuth user) {
        this.user = user;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

   /* public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }*/

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getSessionExpiry() {
        return sessionExpiry;
    }

    public void setSessionExpiry(LocalDateTime sessionExpiry) {
        this.sessionExpiry = sessionExpiry;
    }
}