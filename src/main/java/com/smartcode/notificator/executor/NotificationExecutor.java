package com.smartcode.notificator.executor;

import com.smartcode.notificator.model.entity.NotificationEntity;
import com.smartcode.notificator.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationExecutor {
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 30000)
    public void start() throws InterruptedException {
        List<NotificationEntity> list = notificationService.getReadyNotifications();
        for (NotificationEntity entity : list) {
            notificationService.sendNotifications(entity);
        }
    }
}
