package com.RulesTV.RulesTV.rest.controllers;

import com.RulesTV.RulesTV.entity.Notifications;
import com.RulesTV.RulesTV.repositories.MovieTagRepository;
import com.RulesTV.RulesTV.repositories.NotificationRepository;
import com.RulesTV.RulesTV.services.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationsService notificationsService;

    public NotificationController(NotificationRepository notificationRepository, NotificationsService notificationsService){
        this.notificationRepository = notificationRepository;
        this.notificationsService = notificationsService;
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Notifications>> getNotificationByUser(@PathVariable String userEmail){
        return ResponseEntity.ok(notificationsService.getNotificationByUser(userEmail));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Integer id) {
        notificationsService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read.");
    }

    @PostMapping
    public ResponseEntity<Notifications> createNotification(@RequestBody Notifications notification){
        return ResponseEntity.ok(notificationsService.createNotification(notification));
    }



}
