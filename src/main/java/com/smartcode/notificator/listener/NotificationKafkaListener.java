package com.smartcode.notificator.listener;

import com.smartcode.notificator.model.dto.CreateNotificationDto;
import com.smartcode.notificator.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "notify",
            containerFactory = "kafkaListenerContainerFactory",
            autoStartup = "${listen.auto.start:false}")
    public void CreateListener(@Payload ArrayList<CreateNotificationDto> list) {
        for (CreateNotificationDto createNotificationDto : list) {
            notificationService.create(createNotificationDto);
        }
    }
}