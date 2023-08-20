package com.smartcode.notificator.service.notification;

import com.smartcode.notificator.model.dto.CreateNotificationDto;
import com.smartcode.notificator.model.dto.NotificationDto;
import com.smartcode.notificator.model.dto.VerifyNotificationDto;
import com.smartcode.notificator.model.entity.NotificationEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface NotificationService{

    NotificationDto create(CreateNotificationDto notificationDto);

    List<NotificationDto> getNotifications(Boolean ready, Integer userId);

    List<NotificationEntity> getReadyNotifications();

    void sendNotifications(NotificationEntity entity) throws InterruptedException;


    void verify(VerifyNotificationDto dto);
}
