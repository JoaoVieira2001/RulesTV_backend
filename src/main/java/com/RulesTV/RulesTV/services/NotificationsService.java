package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.Notifications;
import com.RulesTV.RulesTV.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationsService {

    private final NotificationRepository notificationRepository;

    public NotificationsService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notifications> getNotificationByUser(String userEmail){
        return notificationRepository.findByPerformedByEmail(userEmail);
    }

    public void markAsRead(Integer id){
        Notifications notification = notificationRepository.findById(id).orElseThrow();
        notification.setStatus(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public Notifications createNotification(Notifications notification){
        return notificationRepository.save(notification);
    }
}


