package com.smartcode.notificator.service;

import com.smartcode.notificator.model.CreateNotificationDto;
import com.smartcode.notificator.model.NotificationDto;
import com.smartcode.notificator.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;


public interface NotificationService{

    NotificationDto create(CreateNotificationDto notificationDto);

    List<NotificationDto> getNotifications(Boolean ready, Integer userId);

    List<NotificationEntity> getReadyNotifications();

    void sendNotifications(NotificationEntity entity);
}
